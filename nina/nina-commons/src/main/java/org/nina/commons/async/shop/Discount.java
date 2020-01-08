package org.nina.commons.async.shop;

/**
 * 
 * @author riverplant 模拟商品折扣
 */
public enum Discount {
	NONE(0), 
	SILVER(5), 
	GOLD(10), 
	PLATINUM(15), 
	DIAMENT(20);

	private final int percent;

	
	public int getPercent() {
		return percent;
	}


	private Discount(int percent) {
		this.percent = percent;
	}

}
