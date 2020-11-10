package com.riverplant.rabbit.common.constant;

public enum BrokerMessageStatus {

	SENDING("0"),
	SEND_OK("1"),
	SEND_FAIL("2"),//确认失败，例如磁盘已满,不需要重试
	SEND_FAIL_A_MOMENT("3");//因为暂时原因失败，例如sessioin busy,需要重试
	
	private String code;
	
	private BrokerMessageStatus(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	
}
