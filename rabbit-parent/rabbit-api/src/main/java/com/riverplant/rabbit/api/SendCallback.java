package com.riverplant.rabbit.api;
/**
 * $SendCallback 发送消息的回调.回调函数的处理
 * @author riverplant
 *
 */
public interface SendCallback {
	
	void onSuccess();
	
	void onFailure();
}
