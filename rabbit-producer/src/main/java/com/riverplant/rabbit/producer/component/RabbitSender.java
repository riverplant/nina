package com.riverplant.rabbit.producer.component;

import java.util.Map;
import java.util.UUID;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class RabbitSender {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	/**
	 * 确认消息的回调监听接口，用于确认消息是否被block收到
	 */
	final ConfirmCallback confirmCallback = new ConfirmCallback() {
		/**
		 * @param CorrelationData：唯一的标识
		 * @param ack
		 *            broker是否落盘成功
		 * @param cause
		 *            失败的一些异常信息
		 */
		@Override
		public void confirm(CorrelationData correlationData, boolean ack, String cause) {
           System.out.println("消息ACK结果:"+ack + ",correlationData:"+correlationData.getId());
		}
	};

	/**
	 * 发送消息的方法
	 * 
	 * @param message
	 *            具体的消息内容
	 * @param properties
	 *            额外的附加属性
	 * @throws exception
	 */
	public void send(Object message, Map<String, Object> properties) {
		// 在消息头中存放附加属性
		MessageHeaders mhs = new MessageHeaders(properties);
		// MessageBuilder：springboot提供的消息构造器
		Object msg = MessageBuilder.createMessage(message, mhs);
		// confirm模式
		rabbitTemplate.setConfirmCallback(confirmCallback);
		// 指定业务唯一的id
		CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

		MessagePostProcessor mpp = new MessagePostProcessor() {

			@Override
			public Message postProcessMessage(Message message) throws AmqpException {
				System.out.println("post to do: " + message);
				return message;
			}

		};
		rabbitTemplate.convertAndSend("${spring.rabbitmq.listener.order.exchange.name}", 
				                      "${spring.rabbitmq.listener.order.exchange.key}", 
				                      msg, mpp, correlationData);

	}
}
