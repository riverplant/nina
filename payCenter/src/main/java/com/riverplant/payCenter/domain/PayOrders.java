package com.riverplant.payCenter.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
/**
 * 支付中心订单表
 * @author riverplant
 *
 */
@Entity(name = "pay_orders")
public class PayOrders extends DomainImpl{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "merchat_order_id", nullable = false)
	private Long merchantOrderId;
	
	@Column(name = "merchant_user_id", nullable = false)
	private Long merchantUserId;
	
	/**
	 * 最终的价格，订单总金额+邮费
	 */
	@Column(name = "amount")
	private Integer amount;
	
	@Column(name = "pay_method")
	private Integer payMethod;
	
	/**
	 * 10：未支付
	 * 20：已支付
	 * 30: 支付失败
	 * 40： 已退款
	 */
	@Column(name = "pay_status")
	private Integer payStatus;
	
	@Column(name = "come_from")
	private String comeFrom;
	
	@Column(name = "return_url")
	private String returnUrl;
	
	/**
	 * 逻辑删除
	 * 1：删除
	 * 0：未删除
	 */
	@Column(name = "is_delete")
	private Integer isDelete;


	public Long getMerchantOrderId() {
		return merchantOrderId;
	}

	public void setMerchantOrderId(Long merchantOrderId) {
		this.merchantOrderId = merchantOrderId;
	}

	public Long getMerchantUserId() {
		return merchantUserId;
	}

	public void setMerchantUserId(Long merchantUserId) {
		this.merchantUserId = merchantUserId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(Integer payMethod) {
		this.payMethod = payMethod;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getComeFrom() {
		return comeFrom;
	}

	public void setComeFrom(String comeFrom) {
		this.comeFrom = comeFrom;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public PayOrders() {
		
	}
}
