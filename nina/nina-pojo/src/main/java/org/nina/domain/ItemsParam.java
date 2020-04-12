package org.nina.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
/**
 * 产品参数表
 * @author riverplant
 *
 */
@Entity
public class ItemsParam extends DomainImpl {

	private static final long serialVersionUID = 1L;

	@OneToOne(targetEntity = Items.class, cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@JoinColumn(name = "item_id", referencedColumnName = "id")
	private Items items;
    /**
     * 产地
     */
	@JoinColumn(name = "produc_place")
	private String producPlace;
    /**
     * 保质期
     */
	@JoinColumn(name = "foot_period")
	private String footPeriod;
    /**
     * 品牌
     */
	@JoinColumn(name = "brand")
	private String brand;
    
	/**
	 * 生产厂名
	 */
	@JoinColumn(name = "factory_name")
	private String factoryName;
    /**
     * 生产厂地址
     */
	@Embedded
	private Address factoryAddresses;
    /**
     * 包装方式
     */
	@JoinColumn(name = "packging_method")
	private String packagingMethod;
    /**
     * 规格重量
     */
	@JoinColumn(name = "weight")
	private String weight;
    /**
     * 存储方式
     */
	@JoinColumn(name = "storage_method")
	private String storageMethod;
    /**
     * 食用方式
     */
	@JoinColumn(name = "eat_method")
	private String eatMethod;

	public Items getItems() {
		return items;
	}

	public void setItems(Items items) {
		this.items = items;
	}

	public String getProducPlace() {
		return producPlace;
	}

	public void setProducPlace(String producPlace) {
		this.producPlace = producPlace;
	}

	public String getFootPeriod() {
		return footPeriod;
	}

	public void setFootPeriod(String footPeriod) {
		this.footPeriod = footPeriod;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public Address getFactoryAddresses() {
		return factoryAddresses;
	}

	public void setFactoryAddresses(Address factoryAddresses) {
		this.factoryAddresses = factoryAddresses;
	}

	public String getPackagingMethod() {
		return packagingMethod;
	}

	public void setPackagingMethod(String packagingMethod) {
		this.packagingMethod = packagingMethod;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getStorageMethod() {
		return storageMethod;
	}

	public void setStorageMethod(String storageMethod) {
		this.storageMethod = storageMethod;
	}

	public String getEatMethod() {
		return eatMethod;
	}

	public void setEatMethod(String eatMethod) {
		this.eatMethod = eatMethod;
	}

}
