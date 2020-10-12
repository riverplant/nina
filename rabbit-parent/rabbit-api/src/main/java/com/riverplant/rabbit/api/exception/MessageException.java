package com.riverplant.rabbit.api.exception;
/**
 * 定义消息的基础异常类
 * @author riverplant
 * $MessageException
 */
public class MessageException extends Exception{

	private static final long serialVersionUID = -6146967279907412484L;

	public MessageException() {
		super();
	}
	
	public MessageException(String message) {
		super(message);
	}
	
	public MessageException(String message,Throwable cause) {
		super(message, cause);
	}
	
	public MessageException(Throwable cause) {
		super(cause);
	}
}
