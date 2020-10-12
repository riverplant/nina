package com.riverplant.rabbit.core.producer.broker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.riverplant.rabbit.api.MessageProducer;
import com.riverplant.rabbit.api.SendCallback;
import com.riverplant.rabbit.api.exception.MessageRunTimeException;
import com.riverplant.rabbit.api.model.Message;
import com.riverplant.rabbit.api.model.MessageType;

/**
 * $ProducerClient 发送消息的实现类
 * @author riverplant
 *
 */
@Component
public class ProducerClient implements MessageProducer{
    @Autowired RabbitBroker rabbitBroker;
	@Override
	public void send(Message message) throws MessageRunTimeException {
		Map<String, Object> properties = new HashMap<>();
		//验证信息的topic
		Preconditions.checkNotNull(message.getTopic());
		String messageType = message.getMessageType();
		switch (messageType) {
		case MessageType.RAPID:
			rabbitBroker.rapidSend(message, properties);
		break;
		case MessageType.CONFIRM:
			rabbitBroker.confirmSend(message, properties);
			break;
		case MessageType.RELIANT:
			rabbitBroker.reliantSend(message, properties);
			break;
		default:
			break;
		}
		
	}

	@Override
	public void send(List<Message> messages) throws MessageRunTimeException {
		
		
	}

	@Override
	public void send(Message messages, SendCallback callback) throws MessageRunTimeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void send(List<Message> messages, SendCallback callback) throws MessageRunTimeException {
		// TODO Auto-generated method stub
		
	}

}
