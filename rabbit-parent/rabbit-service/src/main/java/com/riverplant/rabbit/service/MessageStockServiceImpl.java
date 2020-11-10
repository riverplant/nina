package com.riverplant.rabbit.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.riverplant.rabbit.common.constant.BrokerMessageStatus;
import com.riverplant.rabbit.domain.BrokerMessage;
import com.riverplant.rabbit.repository.BrokerMessageRepository;
/**
 * 通过定义transactionManager.commit来保持上下文的一致性
 * @author riverplant
 *
 */
@Service
@Transactional
public class MessageStockServiceImpl implements MessageStockService {

	@Autowired private BrokerMessageRepository brokerMessageRepository;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public BrokerMessage insert(BrokerMessage brokerMessage) {
		
		return brokerMessageRepository.saveAndFlush(brokerMessage);
	}

	@Override
	public void success(String messageId) {
		
		brokerMessageRepository.changeBrokerMessageStatus(messageId, BrokerMessageStatus.SENDING.getCode(), BrokerMessageStatus.SEND_OK.getCode(),new Date());
	}
	
	@Override
	public void failure(String messageId) {
		
		brokerMessageRepository.changeBrokerMessageStatus(messageId, BrokerMessageStatus.SENDING.getCode(),BrokerMessageStatus.SEND_FAIL.getCode(),new Date());
	}
}