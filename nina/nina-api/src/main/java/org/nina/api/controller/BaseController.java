package org.nina.api.controller;

import java.io.File;

import org.nina.domain.Orders;
import org.nina.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public abstract class BaseController {

	public static final String FOODIE_SHOPCART = "shopcart";
	public static final Integer COMMENT_PAGE_SIZE = 10;
	public static final Integer PAGE_SIZE = 20;
	// 微信支付成功 -> 支付中心-> 自己的应用平台->回调通知
	public static final String payReturnUrl = "/orders/notifyMerchantOrderPaide";
	// 支付中心的调用地址,单独部署的项目,需要企业资质
	public static final String paymentUrl = "";
	// 用户上传头像的位置
	public static final String IMAGE_USER_FACE_LOCATION =  File.separator+"riverplant"
														   +File.separator+"nina"
														   +File.separator+"images"
														   +File.separator+"faces";
	public static final String SHOPCART = "shopcart";
	@Autowired public OrderService orderService;
	protected Orders checkUserOrder(Long userId, Long orderId) {
		
		return orderService.checkUserOrder(userId, orderId);
	}

}
