package com.riverplant.rabbit.api.exception;
/**
 * $MessageRunTimeException
 * @author riverplant
 *
 */
public class MessageRunTimeException extends RuntimeException{

	private static final long serialVersionUID = 1746893192946020552L;

	public MessageRunTimeException() {
		super();
	}
	
	public MessageRunTimeException(String message) {
		super(message);
	}
	
	public MessageRunTimeException(String message,Throwable cause) {
		super(message, cause);
	}
	
	public MessageRunTimeException(Throwable cause) {
		super(cause);
	}
}
