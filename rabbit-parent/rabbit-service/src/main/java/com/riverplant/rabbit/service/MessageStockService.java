package com.riverplant.rabbit.service;

import com.riverplant.rabbit.domain.BrokerMessage;

public interface MessageStockService {

	public BrokerMessage insert(BrokerMessage brokerMessage);

	public void success(String messageId);
	
	public void failure(String messageId);
}
