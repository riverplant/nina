package org.nina.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 商品图片
 * 
 * @author jli
 *
 */
@Entity
public class Items_img extends DomainImpl {

	private static final long serialVersionUID = 1L;
	/**
	 * 商品外键id
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Items items;

	@Column(name = "url")
	private String url;

	@Column(name = "sort")
	private Integer sort;

	@Column(name = "is_main")
	boolean isMain;

	public Items getItems() {
		return items;
	}

	public void setItems(Items items) {
		this.items = items;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public boolean isMain() {
		return isMain;
	}

	public void setMain(boolean isMain) {
		this.isMain = isMain;
	}

}
