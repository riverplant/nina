package org.nina.commons.enums;

/**
 * 支付方式
 * 
 * @author riverplant
 *
 */
public enum PayMethod {
	WEIXIN(1, "微信"), ALIPAY(2, "支付宝");
	public final Integer trype;
	public final String value;

	private PayMethod(Integer trype, String value) {
		this.trype = trype;
		this.value = value;
	}
}
