package org.nina.commons.async.shop;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author riverplant 模拟报价信息
 */
public class Quote {

	private final String SHOP;
	private final double price;
	private final Discount discount;

	
	public String getSHOP() {
		return SHOP;
	}

	public double getPrice() {
		return price;
	}

	public Discount getDiscount() {
		return discount;
	}

	public Quote(String sHOP, double price, Discount discount) {
		SHOP = sHOP;
		this.price = price;
		this.discount = discount;
	}

	public static Quote parse(String content) {
		String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(content, ":");
		return new Quote(items[0],Double.parseDouble(items[1]),Discount.valueOf(items[2]));
	}
}
