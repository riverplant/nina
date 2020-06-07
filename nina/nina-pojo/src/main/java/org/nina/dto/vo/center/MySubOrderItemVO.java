package org.nina.dto.vo.center;
/**
 * 用户中心，我的订单列表中的子列表
 * @author riverplant
 *
 */
public class MySubOrderItemVO {

	private Long itemId;
	private String itemName;
	private String itemImg;
	private Long itemSpecId;
	private String itemSpecName;
	private Integer buyCounts;
	private Integer price;

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

	public String getItemImg() {
		return itemImg;
	}

	public void setItemImg(String itemImg) {
		this.itemImg = itemImg;
	}

	public Long getItemSpecId() {
		return itemSpecId;
	}

	public void setItemSpecId(Long itemSpecId) {
		this.itemSpecId = itemSpecId;
	}

	public String getItemSpecName() {
		return itemSpecName;
	}

	public void setItemSpecName(String itemSpecName) {
		this.itemSpecName = itemSpecName;
	}

	public Integer getBuyCounts() {
		return buyCounts;
	}

	public void setBuyCounts(Integer buyCounts) {
		this.buyCounts = buyCounts;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

}
