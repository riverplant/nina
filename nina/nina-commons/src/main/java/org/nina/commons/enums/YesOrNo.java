package org.nina.commons.enums;

/**
 * 是否枚举
 * 
 * @author riverplant
 *
 */
public enum YesOrNo {
	NO(0, "否"), YES(1, "是");
	public final Integer trype;
	public final String value;

	private YesOrNo(Integer trype, String value) {
		this.trype = trype;
		this.value = value;
	}
}
