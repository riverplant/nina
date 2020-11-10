package org.nina.dto.vo;

import java.util.List;

public class OrderVO {

	private String orderId;
    //用于用户自己开发的支付中心的表单
	private PayOrdersVO payOrdersVO;

	private List<ShopcartVO> toBeRemoveShopCartItems;

	public List<ShopcartVO> getToBeRemoveShopCartItems() {
		return toBeRemoveShopCartItems;
	}

	public void setToBeRemoveShopCartItems(List<ShopcartVO> toBeRemoveShopCartItems) {
		this.toBeRemoveShopCartItems = toBeRemoveShopCartItems;
	}

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
