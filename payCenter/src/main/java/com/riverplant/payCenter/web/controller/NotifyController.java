package com.riverplant.payCenter.web.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nina.commons.enums.OrderStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.riverplant.payCenter.domain.PayResult;
import com.riverplant.payCenter.service.PaymentOrderServiceImpl;
import com.riverplant.payCenter.service.WeixinOrderServiceImpl;

/**
 * 异步通知
 * 
 * @author riverplant
 *
 */
@RestController
@RequestMapping(value = "weixin/nitofy")
public class NotifyController {
	@Autowired
	private WeixinOrderServiceImpl weixinOrderServiceImpl;
	@Autowired
	private PaymentOrderServiceImpl paymentOrderServiceImpl;
	@Autowired
	private RestTemplate restTemplate;

	@PostMapping(value = "/wxpay")
	public void wxpay(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//获取微信支付结果
		PayResult payResult = weixinOrderServiceImpl.getWxPayResult(request.getInputStream());
		boolean isPaid = payResult.getReturn_code().equals("SUCCESS") ? true : false;
		//查询订单在微信师傅支付成功
		//支付成功，商户处理后同步返回给微信参数
		PrintWriter writer = response.getWriter();
		if(isPaid) {
			String merchantOrderId = payResult.getOut_trade_no();
			String wxFlowId = payResult.getTransaction_id();
			Integer paidAmount = payResult.getTotal_fee();
			//支付成功，修改订单状态
			String merchantReturnUrl = paymentOrderServiceImpl.updateOrderPaid(merchantOrderId,paidAmount,OrderStatusEnum.WAIT_DELIVER);
			MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
			requestEntity.add("merchantOrderId", merchantOrderId);
			String httpStatus = restTemplate.postForObject(merchantReturnUrl, requestEntity, String.class);
			String noticeStr = setXML("SUCCESS","");
			writer.write(noticeStr);
		}else {
			//支付失败
			String noticeStr = setXML("FAIL","");
			writer.write(noticeStr);
		}
		writer.flush();
	}
	public static String setXML(String return_code, String return_msg) {
		return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg + "]]></return_msg></xml>";
	}
}
