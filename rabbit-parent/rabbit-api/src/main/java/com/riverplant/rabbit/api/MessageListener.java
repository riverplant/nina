package com.riverplant.rabbit.api;

import com.riverplant.rabbit.api.model.Message;
/**
 * $MessageListener 消费者监听消息
 * @author riverplant
 *
 */
public interface MessageListener {
	
	void onMessage(Message message);
}
