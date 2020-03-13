package org.nina.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * 轮播图
 * 
 * @author riverplant
 *
 */
@Entity
public class Carousel extends DomainImpl {
	private static final long serialVersionUID = 1L;

	/**
	 * 图片 图片地址
	 */
	@Column(name = "image_url")
	private String imageUrl;

	/**
	 * 背景色
	 */
	@Column(name = "background_color")
	private String backgroundColor;

	/**
	 * 商品id 商品id
	 */
	@Column(name = "item_id")
	private String itemId;

	/**
	 * 商品分类
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cat_id")
	@MapsId
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
	@Column(name = "is_show")
	private Boolean isShow;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "carousel")
	// @OrderBy("carousel.id ASC")设置排序方式
	private Set<ItemsCarouselAssociation> items;

	public Set<ItemsCarouselAssociation> getItems() {
		return items;
	}

	public void setItems(Set<ItemsCarouselAssociation> items) {
		this.items = items;
	}

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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
