package com.riverplant.rabbit.core.producer.broker;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.riverplant.rabbit.api.exception.MessageRunTimeException;
import com.riverplant.rabbit.api.model.Message;
import com.riverplant.rabbit.api.model.MessageType;
import com.riverplant.rabbit.common.convert.GenericMessageConverter;
import com.riverplant.rabbit.common.convert.RabbitMessageConverter;
import com.riverplant.rabbit.common.serializer.Serializer;
import com.riverplant.rabbit.common.serializer.SerializerFactory;
import com.riverplant.rabbit.common.serializer.impl.JacksonSerializerFactory;

/**
 * $RabbitTemplateContainer RabbitTemplate池化
 * 
 * @author riverplant
 *
 */
@Component
public class RabbitTemplateContainer {
	
	private Logger log = LoggerFactory.getLogger(RabbitTemplateContainer.class);
    //之前的correlationData的id使用messageId+#+时间戳组成
	private Splitter splitter = Splitter.on("#");
	
	private SerializerFactory serializerFactory = JacksonSerializerFactory.instance;
	//1.池化
	private Map<String/*Topic*/,RabbitTemplate> rabbitMap = Maps.newConcurrentMap();
	
	//2.连接工厂
	@Autowired ConnectionFactory connectionFactory;
	
	//3.创建获得RabbitTemplate方法
	public RabbitTemplate getTemplate(Message message ) throws MessageRunTimeException{
		Preconditions.checkNotNull(message.getTopic());
		String topic = message.getTopic();
		RabbitTemplate template = rabbitMap.get(message.getTopic());
		if(template != null ) {
			//已经存在池中，直接返回
			return template;
		}
		//不存在池中，需要创建
		log.info("topic: {} is not exists,create one",topic);
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setExchange(topic);
		rabbitTemplate.setRoutingKey(message.getRoutingKey());
		rabbitTemplate.setRetryTemplate(new RetryTemplate());
		//提加序列化和反序列化，converter对象
		Serializer  serializer  = serializerFactory.create();
		GenericMessageConverter gmc = new GenericMessageConverter(serializer);
		RabbitMessageConverter rmc = new RabbitMessageConverter(gmc);
		rabbitTemplate.setMessageConverter(rmc);
		
		//判断是否需要confirm,除了迅速消息都需要
		String messageType = message.getMessageType();
		if(!MessageType.RAPID.equals(messageType)) {
			rabbitTemplate.setConfirmCallback(confirmCallback);
		}
		
		rabbitMap.putIfAbsent(topic, rabbitTemplate);
		return rabbitMap.get(topic);
	}
   
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
			List<String>strings  = splitter.splitToList(correlationData.getId());
			String messageId = strings.get(0);			
			long sendTime = Long.parseLong(strings.get(1));
			
			if(ack) {//成功返回结果
				log.info("send message is success,confirm messageId:{}, send time is {}", messageId, sendTime);
			} else {//
				log.error("send message is faild,confirm messageId:{}, send time is {}", messageId, sendTime);
			}
			System.out.println("消息ACK结果:"+ack + ",correlationData:"+correlationData.getId());
		}
	};	
}
