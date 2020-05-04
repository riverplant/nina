package org.nina.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.ColumnDefault;

/**
 * 
 * @author riverplant 可嵌入对象
 */
@Embeddable
public class Address {
	/**
	 * 省份
	 */
	private String province;

	/**
	 * 城市
	 */
	private String city;

	/**
	 * 区县
	 */
	private String district;

	/**
	 * 详细地址
	 */
	private String address;
	/**
	 * zipcode
	 */
	private String zipcode;

	/**
	 * 扩展字段
	 */
	private String extand;
	/**
	 * 是否默认地址 0 1
	 */
	@Column(name = "is_default")
	@ColumnDefault(value = "0")
	private Integer isDefault;

	@Column(name = "address_order")
	private Integer addressOrder;

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public Integer getAddressOrder() {
		return addressOrder;
	}

	public void setAddressOrder(Integer addressOrder) {
		this.addressOrder = addressOrder;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getExtand() {
		return extand;
	}

	public void setExtand(String extand) {
		this.extand = extand;
	}

}
