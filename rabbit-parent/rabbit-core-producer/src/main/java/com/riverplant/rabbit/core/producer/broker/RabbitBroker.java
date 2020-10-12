package com.riverplant.rabbit.core.producer.broker;

import java.util.Map;

import com.riverplant.rabbit.api.model.Message;

/**
 * $RabbitBroker 具体发送消息的接口
 * @author riverplant
 *
 */
public interface RabbitBroker {

	void rapidSend(Message message, Map<String, Object> properties);//发送迅速消息
	
	void confirmSend(Message message, Map<String, Object> properties);//发送confirm消息
	
	void reliantSend(Message message, Map<String, Object> properties);//发送可靠性消息
	
	void SendMessages();//发送消息列表
}
