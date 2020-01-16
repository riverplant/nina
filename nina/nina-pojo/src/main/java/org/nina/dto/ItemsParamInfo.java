package org.nina.dto;

import java.io.Serializable;

import org.nina.domain.Address;
/**
 * 
 * @author jli
 *
 */
public class ItemsParamInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private ItemsInfo items;

	private String producPlace;

	private String footPeriod;

	private String brand;

	private String factoryName;

	private Address factoryAddresses;

	private String packagingMethod;

	private String weight;

	private String storageMethod;

	private String eatMethod;

	public ItemsInfo getItems() {
		return items;
	}

	public void setItems(ItemsInfo items) {
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
