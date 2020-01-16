package org.nina.dto;

import org.nina.domain.Category;

public class CarouselInfo {

	/**
	 * 图片 图片地址
	 */
	private String imageUrl;

	/**
	 * 背景色
	 */
	private String backgroundColor;

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

	/**
	 * 轮播图展示顺序
	 */
	private Integer sort;

	/**
	 * 是否展示
	 */
	private Boolean isShow;
	private Long id;

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
