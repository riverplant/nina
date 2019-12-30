package org.nina.dto;

import javax.validation.constraints.NotBlank;

import org.nina.domain.Category;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * 
 * @author riverplant
 *
 */
public class ItemsInfo {
	public interface ItemsListView{};
	public interface ItemsDetailView extends ItemsListView{};
	
	private Long id;

	@NotBlank
	private String itemName;

	/**
	 * 尽可能用外键来维护多对一的关系
	 */
	private Category category;

	/**
	 * 一级分类外键id
	 */
	private Integer rootCatId;

	/**
	 * 累计销售 累计销售
	 */
	private Integer sellCounts;

	/**
	 * 上下架状态 上下架状态,1:上架 2:下架
	 */
	private Integer onOffStatus;

	@JsonView(ItemsListView.class)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonView(ItemsListView.class)
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@JsonView(ItemsListView.class)
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@JsonView(ItemsListView.class)
	public Integer getRootCatId() {
		return rootCatId;
	}

	public void setRootCatId(Integer rootCatId) {
		this.rootCatId = rootCatId;
	}

	@JsonView(ItemsDetailView.class)
	public Integer getSellCounts() {
		return sellCounts;
	}

	public void setSellCounts(Integer sellCounts) {
		this.sellCounts = sellCounts;
	}
	
	@JsonView(ItemsDetailView.class)
	public Integer getOnOffStatus() {
		return onOffStatus;
	}

	public void setOnOffStatus(Integer onOffStatus) {
		this.onOffStatus = onOffStatus;
	}

	
}
