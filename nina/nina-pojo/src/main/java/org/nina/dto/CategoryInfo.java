package org.nina.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import org.nina.domain.Carousel;
import org.nina.domain.Items;

/**
 * 针对分库分表
 * 
 * @author jli
 *
 */
public class CategoryInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	/**
	 * 分类名称
	 */
	@NotBlank
	private String name;

	/**
	 * 分类类型
	 */
	@NotBlank
	private Integer type;

	/**
	 * 父id
	 */
	private List<CategoryInfo> subCategory;

	/**
	 * 图标
	 */
	private String logo;

	/**
	 * 口号
	 */
	private String slogan;

	/**
	 * 分类图
	 */
	private String catImage;

	/**
	 * 背景颜色
	 */
	private String bgColor;
	/**
	 * mappedBy = "category":一对多关系的维护交给many端的category来维护
	 */
	private Set<Items> items;

	/**
	 * 轮播图外键id
	 */
	private Carousel carousel;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public String getCatImage() {
		return catImage;
	}

	public void setCatImage(String catImage) {
		this.catImage = catImage;
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public Set<Items> getItems() {
		return items;
	}

	public void setItems(Set<Items> items) {
		this.items = items;
	}

	public Carousel getCarousel() {
		return carousel;
	}

	public void setCarousel(Carousel carousel) {
		this.carousel = carousel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<CategoryInfo> getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(List<CategoryInfo> subCategory) {
		this.subCategory = subCategory;
	}

}
