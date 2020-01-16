package org.nina.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品规格表
 * 
 * @author jli
 *
 */
public class ItemsSpecInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private ItemsInfo items;

	/**
	 * 规格名称
	 */
	private String name;

	/**
	 * 库存
	 */
	private Integer stock;

	/**
	 * 折扣力度
	 */
	private BigDecimal discounts;

	/**
	 * 优惠价
	 */
	private Integer priceDiscount;

	/**
	 * 原价
	 */
	private Integer priceNormal;

	public ItemsInfo getItems() {
		return items;
	}

	public void setItems(ItemsInfo items) {
		this.items = items;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public BigDecimal getDiscounts() {
		return discounts;
	}

	public void setDiscounts(BigDecimal discounts) {
		this.discounts = discounts;
	}

	public Integer getPriceDiscount() {
		return priceDiscount;
	}

	public void setPriceDiscount(Integer priceDiscount) {
		this.priceDiscount = priceDiscount;
	}

	public Integer getPriceNormal() {
		return priceNormal;
	}

	public void setPriceNormal(Integer priceNormal) {
		this.priceNormal = priceNormal;
	}

}
