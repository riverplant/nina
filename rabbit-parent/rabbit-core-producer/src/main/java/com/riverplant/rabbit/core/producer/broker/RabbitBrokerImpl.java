package com.riverplant.rabbit.core.producer.broker;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.riverplant.rabbit.api.model.Message;
import com.riverplant.rabbit.api.model.MessageType;
import com.riverplant.rabbit.common.constant.BrokerMessageConst;
import com.riverplant.rabbit.common.constant.BrokerMessageStatus;
import com.riverplant.rabbit.domain.BrokerMessage;
import com.riverplant.rabbit.service.MessageStockService;

import lombok.extern.slf4j.Slf4j;

/**
 * $RabbitBrokerImpl 发送不同消息的实现类
 * 
 * @author riverplant
 *
 */
@Slf4j
@Component
public class RabbitBrokerImpl implements RabbitBroker {
	@Autowired RabbitTemplateContainer rabbitTemplateContainer;
	@Autowired MessageStockService messageStockService;
	
	Logger log = LoggerFactory.getLogger(RabbitBrokerImpl.class);

	// 迅速快速消息
	@Override
	public void rapidSend(Message message, Map<String, Object> properties) {
		message.setMessageType(MessageType.RAPID);
		sendKernel(message, properties);

	}

	/**
	 * 异步发送!!!!!!!!!!!!! $sendKernel 发送消息的核心方法，不对外暴露
	 * 
	 * @param message
	 */
	private void sendKernel(Message message, Map<String, Object> properties) {
		AsyncBaseQueue.submit(() -> {
			// 在消息头中存放附加属性
			MessageHeaders mhs = new MessageHeaders(properties);
			// MessageBuilder：springboot提供的消息构造器
			Object msg = MessageBuilder.createMessage(message, mhs);
			CorrelationData correlationData = new CorrelationData(
					String.format("%s#%s", message.getMessageId(), System.currentTimeMillis()));
			String topic = message.getTopic();
			String routingKey = message.getRoutingKey();
			RabbitTemplate rabbitTemplate = rabbitTemplateContainer.getTemplate(message);
			rabbitTemplate.convertAndSend(topic, routingKey, msg, correlationData);
			log.info("#RabbitBrokerImpl.send# to rabbitmq,messageId: {}", message.getMessageId());
		});
	}

	@Override
	public void confirmSend(Message message, Map<String, Object> properties) {
		message.setMessageType(MessageType.CONFIRM);
		sendKernel(message, properties);
	}
    /**
     * 可靠性投递【重要】
     * 需要入库操作来实现可靠性
     */
	@Override
	public void reliantSend(Message message, Map<String, Object> properties) {
		
		message.setMessageType(MessageType.RELIANT);
		//1.把数据库的消息发送日志先记录好
		Date now = new Date();
		BrokerMessage brokerMessage = new BrokerMessage();
		brokerMessage.setMessageId(message.getMessageId());
		brokerMessage.setStatus(BrokerMessageStatus.SENDING.getCode());
		//tryCount最开始发送的时候不需要设置
		//下次发送时间=当前时间+最大容忍时间
		brokerMessage.setNextRetry(DateUtils.addMinutes(now, BrokerMessageConst.TIMEOUT));
		brokerMessage.setCreatedTime(now);
		brokerMessage.setUpdatedTime(now);
		brokerMessage.setMessage(message);
		messageStockService.insert(brokerMessage);
		
		//2.真正的发送消息
		sendKernel(message, properties);
	}

	@Override
	public void SendMessages() {
		// TODO Auto-generated method stub

	}

}
