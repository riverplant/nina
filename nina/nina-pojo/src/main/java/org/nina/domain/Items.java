package org.nina.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Items extends DomainImpl {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品名称 商品名称
	 */
	@Column(name = "item_name")
	private String itemName;

	/**
	 * 分类外键id 分类id
	 */
	@Column(name = "cat_id")
	private Integer catId;

	/**
	 * 一级分类外键id
	 */
	@Column(name = "root_cat_id")
	private Integer rootCatId;

	/**
	 * 累计销售 累计销售
	 */
	@Column(name = "sell_counts")
	private Integer sellCounts;

	/**
	 * 上下架状态 上下架状态,1:上架 2:下架
	 */
	@Column(name = "on_off_status")
	private Integer onOffStatus;
}
