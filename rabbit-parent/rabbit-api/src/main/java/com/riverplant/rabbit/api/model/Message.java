package com.riverplant.rabbit.api.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/**
 * 定义消息
 * @author riverplant
 *
 */
public class Message implements Serializable{
	private static final long serialVersionUID = 1L;

	private String messageId;//唯一消息id
	
	private String topic; //消息的主题
	
	private String routingKey = ""; //#消息的路由规则
	
	private Map<String,Object>attributes = new HashMap<>();//定义附加属性
	
	private int delayMills;//定义即时消息或者延时消息,延迟消息的参数配置
	
	
	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getRoutingKey() {
		return routingKey;
	}

	public void setRoutingKey(String routingKey) {
		this.routingKey = routingKey;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public int getDelayMills() {
		return delayMills;
	}

	public void setDelayMills(int delayMills) {
		this.delayMills = delayMills;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	/**
	 * 默认为confirm消息
	 */
	private String messageType = MessageType.CONFIRM;//消息的类型，即时消息或者延时消息

	public Message(String messageId, String topic, String routingKey, Map<String, Object> attributes, int delayMills,
			String messageType) {
		this.messageId = messageId;
		this.topic = topic;
		this.routingKey = routingKey;
		this.attributes = attributes;
		this.delayMills = delayMills;
		this.messageType = messageType;
	}
	
	public Message(String messageId, 
				   String topic, 
				   String routingKey, 
				   Map<String, Object> attributes, 
				   int delayMills) {
		this.messageId = messageId;
		this.topic = topic;
		this.routingKey = routingKey;
		this.attributes = attributes;
		this.delayMills = delayMills;
	}

	public Message() {
		
	}
	
	
}
