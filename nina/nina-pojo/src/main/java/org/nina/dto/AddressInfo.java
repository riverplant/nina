package org.nina.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "地址对象", description = "地址对象,与前端交互")
public class AddressInfo {
	@ApiModelProperty(value = "省", name = "省", required = true)
	private String province;
	@ApiModelProperty(value = "市", name = "市", required = true)
	private String city;
	@ApiModelProperty(value = "行政区划", name = "行政区划", required = false)
	private String district;
	@ApiModelProperty(value = "详细地址", name = "详细地址", required = true)
	private String address;
	@ApiModelProperty(value = "邮政编码", name = "邮政编码", required = true)
	private String zipcode;
	@ApiModelProperty(value = "地址扩展", name = "地址扩展", required = false)
	private String extand;
	@ApiModelProperty(value = "是否默认地址", name = "是否默认地址", required = true)
	private Integer isDefault;
	@ApiModelProperty(value = "地址编号", name = "地址编号", required = true)
	private Integer addressOrder;

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
}
