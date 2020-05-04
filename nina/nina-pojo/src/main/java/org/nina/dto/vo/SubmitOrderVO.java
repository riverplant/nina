package org.nina.dto.vo;

import java.io.Serializable;
/**
 * 用于创建订单的对象
 * @author riverplant
 *
 */
public class SubmitOrderVO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long userId;
	
	private String itemSpecIds;
	
	private String choosedAddressId;
	
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

	public String getChoosedAddressId() {
		return choosedAddressId;
	}

	public void setChoosedAddressId(String choosedAddressId) {
		this.choosedAddressId = choosedAddressId;
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

	public SubmitOrderVO(Long userId, String itemSpecIds, String choosedAddressId, Integer choosedPayMethod,
			String orderRemarker) {
		this.userId = userId;
		this.itemSpecIds = itemSpecIds;
		this.choosedAddressId = choosedAddressId;
		this.choosedPayMethod = choosedPayMethod;
		this.orderRemarker = orderRemarker;
	}

	public SubmitOrderVO() {
		
	}
	
}
