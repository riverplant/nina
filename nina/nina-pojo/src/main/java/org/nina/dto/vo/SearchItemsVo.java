package org.nina.dto.vo;

import java.io.Serializable;

/**
 * 用于展示商品搜索列表结果
 * @author riverplant
 *
 */
public class SearchItemsVo implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long itemId;
	private String itemName;
	private int sellCounts;
	private String imgUrl;
	//数据库中只保存分为单位的数值，转换为元需要前端/100实现
	private int price;
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getSellCounts() {
		return sellCounts;
	}
	public void setSellCounts(int sellCounts) {
		this.sellCounts = sellCounts;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public SearchItemsVo(Long itemId, String itemName, int sellCounts, String imgUrl, int price) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.sellCounts = sellCounts;
		this.imgUrl = imgUrl;
		this.price = price;
	}
	public SearchItemsVo() {
	}	
}
