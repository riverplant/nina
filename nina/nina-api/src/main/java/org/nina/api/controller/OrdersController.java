/**
 * 
 */
package org.nina.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.nina.commons.utils.NinaJsonResult;
import org.nina.dto.vo.ShopcartVO;
import org.nina.dto.vo.SubmitOrderVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author riverplant 当用户未登录：使用cookie 当用户登录： 使用Redis
 */
@Api(value = "订单接口controller", tags = "订单相关的接口API")
@RestController
@RequestMapping("oders")
public class OrdersController {
	/**
	 * 
	 * @param submitOrderVO
	 * @return
	 */
	@ApiOperation(value = "用户创建订单", notes = "用户创建订单", httpMethod = "POST")
	@PostMapping("/create")
	public NinaJsonResult create(
			@RequestBody SubmitOrderVO submitOrderVO) { 
	   
		//1.创建订单
		
		//2.创建订单后，移除购物车种以提交的商品
		
		//3.向支付中心发送当前订单，用于保存支付中心的订单数据
		return  NinaJsonResult.ok() ;
	}
	

}
