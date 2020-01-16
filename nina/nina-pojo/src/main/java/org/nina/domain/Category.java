package org.nina.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

/**
 * 
 * @author riverplant
 *
 */

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "river_name", "river_type", "river_father_id" }) })
public class Category extends DomainImpl {
	private static final long serialVersionUID = 1L;

	/**
	 * 分类名称
	 */
	@Column(name = "name", nullable = false)
	private String name;

	/**
	 * 分类类型:1: 1级分类,根级分类...
	 */
	@Column(name = "type", nullable = false)
	private Integer type;

	/**
	 * 父id
	 */
	@Column(name = "father_id")
	private Long fatherId;

	/**
	 * 图标
	 */
	@Column(name = "logo")
	private String logo;

	/**
	 * 口号
	 */
	@Column(name = "slogan")
	private String slogan;

	/**
	 * 分类图
	 */
	@Column(name = "cat_image")
	private String catImage;

	/**
	 * 背景颜色
	 */
	@Column(name = "bg_color")
	private String bgColor;
	/**
	 * mappedBy = "category":一对多关系的维护交给many端的category来维护
	 */
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "category")
	private Set<Items> items;

	/**
	 * 轮播图外键id
	 */
	@OneToOne(mappedBy = "category")
	private Carousel carousel;

	// 使用了该注解的字段将不会被同步到数据库
	@Transient
	private String xxx;

	public Set<Items> getItems() {
		return items;
	}

	public void setItems(Set<Items> items) {
		this.items = items;
	}

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

	public Long getFatherId() {
		return fatherId;
	}

	public void setFatherId(Long fatherId) {
		this.fatherId = fatherId;
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

	public Carousel getCarousel() {
		return carousel;
	}

	public void setCarousel(Carousel carousel) {
		this.carousel = carousel;
	}

}
