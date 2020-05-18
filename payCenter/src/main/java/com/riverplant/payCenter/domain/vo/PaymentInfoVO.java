package com.riverplant.payCenter.domain.vo;

import java.io.Serializable;

public class PaymentInfoVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String merchantOrderId;
	private String merchantUserId;
	private Integer amount;
	private String qrCodeUrl;
	
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
	
	public String getQrCodeUrl() {
		return qrCodeUrl;
	}
	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}
	public PaymentInfoVO() {
		
	}	
}
