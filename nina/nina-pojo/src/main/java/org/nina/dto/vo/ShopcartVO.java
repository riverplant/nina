package org.nina.dto.vo;

import java.io.Serializable;

/**
 * 接收前端页面传过来的添加到购物车的商品信息
 * 
 * @author riverplant
 *
 */
public class ShopcartVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long itemId;
	private String itemImgUrl;
	private String itemName;
	private Long specId;
	private String specName;
	private int buyCounts;
	private int priceDiscount;
	private int priceNormal;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemImgUrl() {
		return itemImgUrl;
	}

	public void setItemImgUrl(String itemImgUrl) {
		this.itemImgUrl = itemImgUrl;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Long getSpecId() {
		return specId;
	}

	public void setSpecId(Long specId) {
		this.specId = specId;
	}

	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	public int getBuyCounts() {
		return buyCounts;
	}

	public void setBuyCounts(int buyCounts) {
		this.buyCounts = buyCounts;
	}

	public int getPriceDiscount() {
		return priceDiscount;
	}

	public void setPriceDiscount(int priceDiscount) {
		this.priceDiscount = priceDiscount;
	}

	public int getPriceNormal() {
		return priceNormal;
	}

	public void setPriceNormal(int priceNormal) {
		this.priceNormal = priceNormal;
	}

	public ShopcartVO() {
	}

}
