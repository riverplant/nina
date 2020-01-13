package org.nina.dto;

import org.nina.domain.Category;

public class CarouselCondition {

	private boolean isShow;
	/**
	 * 商品id 商品id
	 */
	private String itemId;

	/**
	 * 商品分类id 商品分类id
	 */
	private String catId;

	/**
	 * 商品分类
	 */
	private Category category;

	/**
	 * 轮播图类型 轮播图类型，用于判断，可以根据商品id或者分类进行页面跳转，1：商品 2：分类
	 */
	private Integer type;

	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
