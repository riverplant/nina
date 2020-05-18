package com.riverplant.payCenter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource( 
		name="wxPay.properties",
		value= {"classpath:wxPay.properties"},
		ignoreResourceNotFound=false,
		encoding="UTF-8")
@ConfigurationProperties(prefix = "wxpay")  
public class WeixinPayConfig {

	private String qrcodeKey;
	private String qrcodeExpire;
	private String appId;
	private String merchantId;
	private String secrectKey;
	private String spbillCreateIp;
	private String notifyUrl;
	private String tradType;
	private String placeOrderUrl;
	
	
	
	
	
	public String getPlaceOrderUrl() {
		return placeOrderUrl;
	}
	public void setPlaceOrderUrl(String placeOrderUrl) {
		this.placeOrderUrl = placeOrderUrl;
	}
	public String getTradType() {
		return tradType;
	}
	public void setTradType(String tradType) {
		this.tradType = tradType;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getQrcodeKey() {
		return qrcodeKey;
	}
	public void setQrcodeKey(String qrcodeKey) {
		this.qrcodeKey = qrcodeKey;
	}
	public String getQrcodeExpire() {
		return qrcodeExpire;
	}
	public void setQrcodeExpire(String qrcodeExpire) {
		this.qrcodeExpire = qrcodeExpire;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getSecrectKey() {
		return secrectKey;
	}
	public void setSecrectKey(String secrectKey) {
		this.secrectKey = secrectKey;
	}
	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}
	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}
		
}
