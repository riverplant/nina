package org.nina.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author riverplant
 *
 */
@Entity(name = "order_detail")
public class OrderDetail extends DomainImpl {
	private static final long serialVersionUID = 1L;
	// 归属订单id
	@Column(name = "order_id")
	Long orderId;

	// 商品id
	@Column(name = "item_id")
	Long itemId;

	// 商品图片
	@Column(name = "item_img")
	String itemImg;

	// 商品名称
	@Column(name = "item_name")
	String itemName;

	// 规格id
	@Column(name = "item_spec_id")
	Long itemSpecId;

	// 规格名称
	@Column(name = "item_spec_name")
	String itemSpecName;

	// 成交价格
	@Column(name = "price")
	int price;

	// 商品规格名称
	@Column(name = "buy_counts")
	int buyCounts;

	@ManyToOne
	@JoinColumn(name = "order_id")
	Orders order;

	public Long getOrderId() {
		return orderId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemImg() {
		return itemImg;
	}

	public void setItemImg(String itemImg) {
		this.itemImg = itemImg;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getBuyCounts() {
		return buyCounts;
	}

	public void setBuyCounts(int buyCounts) {
		this.buyCounts = buyCounts;
	}

	public OrderDetail() {
	}

}
