package org.nina.dto.vo;

public class OrderVO {

	private String orderId;
	
	private PayOrdersVO payOrdersVO;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public PayOrdersVO getPayOrdersVO() {
		return payOrdersVO;
	}

	public void setPayOrdersVO(PayOrdersVO payOrdersVO) {
		this.payOrdersVO = payOrdersVO;
	}
	
	
}
