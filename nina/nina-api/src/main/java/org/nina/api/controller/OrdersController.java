/**
 * 
 */
package org.nina.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nina.commons.enums.OrderStatusEnum;
import org.nina.commons.enums.PayMethod;
import org.nina.commons.utils.CookieUtils;
import org.nina.commons.utils.NinaJsonResult;
import org.nina.dto.vo.OrderVO;
import org.nina.dto.vo.PayOrdersVO;
import org.nina.dto.vo.SubmitOrderVO;
import org.nina.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author riverplant 当用户未登录：使用cookie 当用户登录： 使用Redis
 */
@Api(value = "订单接口controller", tags = "订单相关的接口API")
@RestController
@RequestMapping("oders")
public class OrdersController extends BaseController{
	@Autowired
	private OrderService orderService;
	@Autowired
	private RestTemplate restTemplate;
	/**
	 * 
	 * @param submitOrderVO
	 * @return
	 */
	@ApiOperation(value = "用户创建订单", notes = "用户创建订单", httpMethod = "POST")
	@PostMapping("/create")
	public NinaJsonResult create(
			@RequestBody SubmitOrderVO submitOrderVO,
			HttpServletRequest request,
			HttpServletResponse response) { 
		if(submitOrderVO.getChoosedPayMethod() != PayMethod.WEIXIN.trype && submitOrderVO.getChoosedPayMethod() != PayMethod.ALIPAY.trype) {
			return NinaJsonResult.erorMsg("不支持的支付方式");
		}
		//1.创建订单
		OrderVO orderVO = orderService.createOrder(submitOrderVO);
		//2.创建订单后，移除购物车种以提交的商品,当前先处理为清空购物车
		CookieUtils.setCookie(request, response, FOODIE_SHOPCART , "", true);
		//TODO 整合redis之后，完善购物车种的已结算商品清楚，并且同步cookie
		
		//3.向支付中心发送当前订单，用于保存支付中心的订单数据
		PayOrdersVO payOrdersVO = orderVO.getPayOrdersVO();
		payOrdersVO.setReturnUrl(payReturnUrl);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		//设置调用支付中心的用户名和密码，需要企业资质
		headers.add("imoocUserId", "imooc");
		headers.add("password", "imooc");
		
		HttpEntity<PayOrdersVO> entity = 
				new HttpEntity<>(payOrdersVO,headers);
		//发送请求
		ResponseEntity<NinaJsonResult> responseEntity = 
				restTemplate.postForEntity(paymentUrl, entity, NinaJsonResult.class);
		NinaJsonResult paymentResult = responseEntity.getBody();
		if(paymentResult.getStatus() != 200) {
			return NinaJsonResult.erorMsg("支付中心订单创建失败");
		}
		return  NinaJsonResult.ok(orderVO.getOrderId()) ;
	}
	
	/**
	 * 供微信支付中心回调通知,变更订单状态
	 * @param merchantOrderId用户提交的订单id
	 * @return
	 */
	@ApiOperation(value = "供微信支付中心回调", notes = "供微信支付中心回调", httpMethod = "POST")
	@PostMapping("/notifyMerchantOrderPaide")
	public Integer notifyMerchantOrderPaide(String merchantOrderId) { 
		orderService.updateOrderStatus(Long.valueOf(merchantOrderId), OrderStatusEnum.SUCCESS.trype);
		return  HttpStatus.OK.value();
	}
}