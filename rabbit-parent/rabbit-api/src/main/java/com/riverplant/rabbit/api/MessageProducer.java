package com.riverplant.rabbit.api;

import java.util.List;

import com.riverplant.rabbit.api.exception.MessageRunTimeException;
import com.riverplant.rabbit.api.model.Message;

public interface MessageProducer {

	void send(Message message) throws MessageRunTimeException ;
	
	void send (List<Message> messages) throws MessageRunTimeException ;
	/**
	 * $send消息的发送，附带callback执行业务的处理
	 * @param messages
	 * @param callback
	 * @throws MessageRunTimeException
	 */
	void send (Message messages, SendCallback callback) throws MessageRunTimeException ;
	/**
	 * $send消息的批量发送，附带callback执行业务的处理
	 * @param messages
	 * @param callback
	 * @throws MessageRunTimeException
	 */
	void send (List<Message> messages, SendCallback callback) throws MessageRunTimeException ;
}
