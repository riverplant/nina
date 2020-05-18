package com.riverplant.payCenter.service;

import com.riverplant.payCenter.domain.PreOrderResult;

public interface WeixinOrderService {

	PreOrderResult placeOrder(String body, String out_trade_no, String total_fee) throws Exception;
}
