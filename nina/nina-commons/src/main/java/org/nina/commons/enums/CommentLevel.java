package org.nina.commons.enums;

public enum CommentLevel {

	GOOD(3,"好评"),
	NORMAL(2,"中评"),
	BAD(1,"差评");
	
	public final Integer type;
	public final String value;
	private CommentLevel(Integer type, String value) {
		this.type = type;
		this.value = value;
	}	
}
