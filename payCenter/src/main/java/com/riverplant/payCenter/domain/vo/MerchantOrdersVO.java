package com.riverplant.payCenter.domain.vo;

import java.io.Serializable;
/**
 * 支付中心订单对象，用于保存在开发者自己的支付中心数据库
 * @author riverplant
 *
 */
public class MerchantOrdersVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String merchantOrderId;
	private String merchantUserId;
	private Integer amount;
	private Integer payMethod;
	//回调通知地址
	private String returnUrl;
	private Integer isDelete;
	
	private Integer payStatus;
	private String comeFrom;
	
	public String getMerchantOrderId() {
		return merchantOrderId;
	}
	public void setMerchantOrderId(String merchantOrderId) {
		this.merchantOrderId = merchantOrderId;
	}
	public String getMerchantUserId() {
		return merchantUserId;
	}
	public void setMerchantUserId(String merchantUserId) {
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
	public MerchantOrdersVO() {
		
	}	
}
