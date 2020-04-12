package org.nina.commons.enums;

public enum CommentLevel{

	GOOD(1,"好评"),
	NORMAL(2,"中评"),
	BAD(3,"差评");
	
	public final Integer type;
	public final String value;
	private CommentLevel(Integer type, String value) {
		this.type = type;
		this.value = value;
	}
	
	 public Integer getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public static CommentLevel stateOf(int index) {
	        for (CommentLevel e : CommentLevel.values()) {
	            if (e.getType() == index) {
	                return e;
	            }
	        }
	        return null;
	    }    
}
