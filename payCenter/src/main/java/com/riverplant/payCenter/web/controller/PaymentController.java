package com.riverplant.payCenter.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.nina.commons.enums.OrderStatusEnum;
import org.nina.commons.enums.PayMethod;
import org.nina.commons.utils.NinaJsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riverplant.payCenter.domain.PayOrders;
import com.riverplant.payCenter.domain.PreOrderResult;
import com.riverplant.payCenter.domain.vo.PayOrdersVO;
import com.riverplant.payCenter.domain.vo.PaymentInfoVO;
import com.riverplant.payCenter.service.PaymentOrderService;
import com.riverplant.payCenter.service.WeixinOrderServiceImpl;

@RestController
@RequestMapping("payment")
public class PaymentController {
	@Autowired
	private PaymentOrderService paymentOrderService;
	@Autowired
	private WeixinOrderServiceImpl weixinOrderServiceImpl;

	/**
	 * 接收客户订单信息，保存到自己的数据库
	 * 
	 * @param merchantOrder
	 * @return
	 */
	@PostMapping("/createMerchantOrder")
	public NinaJsonResult createMerchantOrder(@RequestBody PayOrdersVO merchantOrder) {
		String merchantOrderId = merchantOrder.getMerchantUserId();
		String merchantUserId = merchantOrder.getMerchantUserId();
		Integer amount = merchantOrder.getAmount();
		Integer payMethod = merchantOrder.getPayMethod();
		String returnUrl = merchantOrder.getReturnUrl();
		if (StringUtils.isBlank(merchantOrderId)) {
			return NinaJsonResult.erorMsg("参数[orderId]不能为空");
		}
		if (StringUtils.isBlank(merchantUserId)) {
			return NinaJsonResult.erorMsg("参数[userId]不能为空");
		}
		if (amount == null || amount < 1) {
			return NinaJsonResult.erorMsg("参数[realPayAmount]不能为空且不能小于1");
		}
		if (payMethod == null) {
			return NinaJsonResult.erorMsg("参数[payMethod]不能为空");
		}
		if (payMethod != PayMethod.ALIPAY.trype && payMethod != PayMethod.WEIXIN.trype) {
			return NinaJsonResult.erorMsg("参数[payMethod]只支持微信支付和支付宝支付");
		}
		if (StringUtils.isBlank(returnUrl)) {
			return NinaJsonResult.erorMsg("参数[returnUrl]不能为空");
		}
		// 保存传过来的商户订单消息
		boolean isSuccess = false;
		try {
			isSuccess = paymentOrderService.createPaymentOrder(merchantOrder);
		} catch (Exception e) {
			e.printStackTrace();
			NinaJsonResult.erorException(e.getMessage());
		}
		if (isSuccess) {
			return NinaJsonResult.ok("商户订单创建成功");
		} else {
			return NinaJsonResult.erorMsg("商户订单创建失败，请重新尝试...");
		}
	}

	/**
	 * 查询订单信息
	 * 
	 * @param merchantOrderId
	 * @param merchantUserId
	 * @return
	 */
	@PostMapping("/getPaymentCenterOrderInfo")
	public NinaJsonResult getPaymentCenterOrderInfo(String merchantOrderId, String merchantUserId) {
		if (StringUtils.isBlank(merchantOrderId) || StringUtils.isBlank(merchantUserId)) {
			return NinaJsonResult.erorMsg("查询参数不能为空");
		}
		PayOrders orderInfo = paymentOrderService.queryOrderInfo(merchantOrderId, merchantUserId);
		return NinaJsonResult.ok(orderInfo);
	}

	/**
	 * 微信扫码支付页面:通过后端拿到支付地址，前端通过一个小插件来生成二维码
	 * 
	 * @param merchantOrderId:订单ID
	 * @param merchantUserId:用户ID
	 * @return
	 */
	@PostMapping("/getWXPayQRCode")
	public NinaJsonResult getWXPayQRCode(String merchantOrderId, String merchantUserId) {
		if (StringUtils.isBlank(merchantOrderId) || StringUtils.isBlank(merchantUserId)) {
			return NinaJsonResult.erorMsg("查询参数不能为空");
		}
		PayOrders waitPayOrder = paymentOrderService.queryOrderByStatus(
				                  merchantOrderId, 
				                  merchantUserId,
				                  OrderStatusEnum.WAIT_PAY);
		// 商品描述
		String body = "付款用户["+merchantUserId+"]";
		// 商户订单号[out_trade_no]:与微信中心参数名匹配
		String out_trade_no = merchantOrderId;
		
		if(waitPayOrder != null) {
		//TODO
		//从redis中获取订单的微信支付二维码，
		String qrCodeUrl = "";
		//如果二维码已经存在redis缓存中,直接获取，如果缓存中没有需要从新生成
		if(StringUtils.isEmpty(qrCodeUrl)) {
			//订单总金额，单位为分
			String total_fee = String.valueOf(waitPayOrder.getAmount());
			//统一下单
			try {
				PreOrderResult result =	weixinOrderServiceImpl.placeOrder(body, out_trade_no, total_fee);
				qrCodeUrl = result.getCode_url();
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}		
		}
		PaymentInfoVO payOrdersVO = new PaymentInfoVO();
		payOrdersVO.setAmount(waitPayOrder.getAmount());
		payOrdersVO.setMerchantOrderId(merchantOrderId);
		payOrdersVO.setMerchantUserId(merchantUserId);
		payOrdersVO.setQrCodeUrl(qrCodeUrl);
		//TODO 存入redis
		
		return NinaJsonResult.ok(payOrdersVO);
		}else {
		return NinaJsonResult.erorMsg("该订单不存在，或者已经支付");
		}	
	}
}
