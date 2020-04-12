package org.nina.dto.vo;

import java.io.Serializable;

/**
 * 用于定义关联的三级分类的List集合
 * 
 * @author jli
 *
 */
public class SubCategoryVO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long subId;
	private String subName;
	private Integer subType;
	private Long subFatherId;

	
	public SubCategoryVO(Long subId, String subName, Integer subType, Long subFatherId) {
		this.subId = subId;
		this.subName = subName;
		this.subType = subType;
		this.subFatherId = subFatherId;
	}
	
	

	public SubCategoryVO() {
		
	}



	public Long getSubId() {
		return subId;
	}

	public void setSubId(Long subId) {
		this.subId = subId;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public Integer getSubType() {
		return subType;
	}

	public void setSubType(Integer subType) {
		this.subType = subType;
	}

	public Long getSubFatherId() {
		return subFatherId;
	}

	public void setSubFatherId(Long subFatherId) {
		this.subFatherId = subFatherId;
	}

}
