package com.riverplant.rabbit.api.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.riverplant.rabbit.api.exception.MessageRunTimeException;

/**
 * 建造者模式
 * 
 * @author riverplant
 *
 */
public class MessageBuilder {

	private String messageId;// 唯一消息id

	private String topic; // 消息的主题

	private String routingKey = ""; // #消息的路由规则

	private Map<String, Object> attributes = new HashMap<>();// 定义附加属性

	private int delayMills;// 定义即时消息或者延时消息,延迟消息的参数配置

	private String messageType = MessageType.CONFIRM;// 消息的类型，即时消息或者延时消息

	/**
	 * 构造函数私有化
	 */
	private MessageBuilder() {

	}

	public static MessageBuilder create() {
		return new MessageBuilder();
	}

	public MessageBuilder withMessageId(String messageId) {
		this.messageId = messageId;
		return this;
	}

	public MessageBuilder withTopic(String topic) {
		this.topic = topic;
		return this;
	}

	public MessageBuilder withRoutingKey(String routingKey) {
		this.routingKey = routingKey;
		return this;
	}

	public MessageBuilder withAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
		return this;
	}

	public MessageBuilder withAttribute(String key, Object value) {
		this.attributes.put(key, value);
		return this;
	}

	public MessageBuilder withDelayMille(int delayMills) {
		this.delayMills = delayMills;
		return this;
	}

	public MessageBuilder withMessageType(String messageType) {
		if (StringUtils.isNoneBlank(messageType)) {
			this.messageType = messageType;
		}
		return this;
	}

	public Message build() {
		//如果没有messageId,则创建一个
		if(StringUtils.isEmpty(messageId)) {
			messageId = UUID.randomUUID().toString();
		}
		if(StringUtils.isEmpty(topic)) {
			throw new MessageRunTimeException("this topic is null");
		}
		Message message = new Message(messageId, topic, routingKey, attributes, delayMills);
		return message;
	}
}
