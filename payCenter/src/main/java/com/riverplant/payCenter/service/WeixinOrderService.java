package com.riverplant.payCenter.service;

import java.io.InputStream;

import com.riverplant.payCenter.domain.PayResult;
import com.riverplant.payCenter.domain.PreOrderResult;

public interface WeixinOrderService {

	PayResult getWxPayResult(InputStream inStream);
	PreOrderResult placeOrder(String body, String out_trade_no, String total_fee) throws Exception;
}
