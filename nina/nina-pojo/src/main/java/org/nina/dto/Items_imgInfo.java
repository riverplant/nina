package org.nina.dto;

import java.io.Serializable;

public class Items_imgInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	/**
	 * 商品外键id
	 */
	private ItemsInfo items;

	private String url;

	private Integer sort;

	boolean isMain;

	public ItemsInfo getItems() {
		return items;
	}

	public void setItems(ItemsInfo items) {
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
