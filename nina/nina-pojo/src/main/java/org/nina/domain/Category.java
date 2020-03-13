package org.nina.domain;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author riverplant
 *
 */

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "type", "father_id" }) })
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

	/*
	 * cascade：为级联操作，里面有级联保存，级联删除等，all为所有 fetch：加载类型，有lazy和eager二种，
	 * eager为急加载，意为立即加载，在类加载时就加载，lazy为慢加载，第一次调用的时候再加载，由于数据量太大，onetomany一般为lazy
	 * mappedBy：这个为manytoone中的对象名，这个不要变哦.指向的是要关联的属性，而不是要关联的类
	 * Set<role>：这个类型有两种，一种为list另一种为set
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentCategory")
	@JoinColumn(name = "father_id") // 根据父级菜单ID，实现自关联（内部其实也就是一对多）
	@JsonIgnore
	private Set<Category> subCategory;

	@ManyToOne
	@JoinColumn(name = "father_id") // 根据父级菜单ID，实现自关联（内部其实也就是一对多）
	private Category parentCategory;

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
//	@OneToOne(cascade = CascadeType.ALL, mappedBy = "category")
//	private Carousel carousel;

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

	public Set<Category> getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(Set<Category> subCategory) {
		this.subCategory = subCategory;
	}

	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}
}
