package org.nina.dto.vo;

import java.io.Serializable;

/**
 * 用于创建订单的对象
 * 
 * @author riverplant
 *
 */
public class SubmitOrderVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long userId;

	private String itemSpecIds;

	private Integer choosedAddressSord;

	private Integer choosedPayMethod;

	private String orderRemarker;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getItemSpecIds() {
		return itemSpecIds;
	}

	public void setItemSpecIds(String itemSpecIds) {
		this.itemSpecIds = itemSpecIds;
	}

	public Integer getChoosedAddressSord() {
		return choosedAddressSord;
	}

	public void setChoosedAddressSord(Integer choosedAddressSord) {
		this.choosedAddressSord = choosedAddressSord;
	}

	public Integer getChoosedPayMethod() {
		return choosedPayMethod;
	}

	public void setChoosedPayMethod(Integer choosedPayMethod) {
		this.choosedPayMethod = choosedPayMethod;
	}

	public String getOrderRemarker() {
		return orderRemarker;
	}

	public void setOrderRemarker(String orderRemarker) {
		this.orderRemarker = orderRemarker;
	}

	public SubmitOrderVO() {

	}

}
