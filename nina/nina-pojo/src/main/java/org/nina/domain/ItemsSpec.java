package org.nina.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "items_spec")
public class ItemsSpec extends DomainImpl {
	private static final long serialVersionUID = 1L;
	 /**
     * 商品外键id
     */
    @OneToOne(mappedBy = "spec")
    private Items items;

    /**
     * 规格名称
     */
    private String name;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 折扣力度
     */
    private BigDecimal discounts;

    /**
     * 优惠价
     */
    @Column(name = "price_discount")
    private Integer priceDiscount;

    /**
     * 原价
     */
    @Column(name = "price_normal")
    private Integer priceNormal;

	public Items getItems() {
		return items;
	}

	public void setItems(Items items) {
		this.items = items;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public BigDecimal getDiscounts() {
		return discounts;
	}

	public void setDiscounts(BigDecimal discounts) {
		this.discounts = discounts;
	}

	public Integer getPriceDiscount() {
		return priceDiscount;
	}

	public void setPriceDiscount(Integer priceDiscount) {
		this.priceDiscount = priceDiscount;
	}

	public Integer getPriceNormal() {
		return priceNormal;
	}

	public void setPriceNormal(Integer priceNormal) {
		this.priceNormal = priceNormal;
	}
    
}
