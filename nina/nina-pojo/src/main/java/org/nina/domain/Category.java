package org.nina.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
/**
 * 
 * @author riverplant
 *
 */

@Entity
public class Category extends DomainImpl {
	private static final long serialVersionUID = 1L;

	/**
	 * 分类名称
	 */
	@Column(name = "name", nullable = false)
	private String name;

	/**
	 * 分类类型
	 */
	@Column(name = "type",nullable = false)
	private Integer type;

	/**
	 * 父id
	 */
	@Column(name = "father_id")
	private Integer fatherId;

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
     
	//使用了该注解的字段将不会被同步到数据库
	@Transient
	private String xxx;
	
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

	public Integer getFatherId() {
		return fatherId;
	}

	public void setFatherId(Integer fatherId) {
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

}
