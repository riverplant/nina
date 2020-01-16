package org.nina.dto.vo;

import java.util.List;

/**
 * 二级分类
 * 
 * @author jli
 *
 */
public class CategoryVO {
	private Long Id;
	private String name;
	private Integer type;
	private Long FatherId;
	private List<SubCategoryVO> subCatList;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getFatherId() {
		return FatherId;
	}

	public void setFatherId(Long fatherId) {
		FatherId = fatherId;
	}

	public List<SubCategoryVO> getSubCatList() {
		return subCatList;
	}

	public void setSubCatList(List<SubCategoryVO> subCatList) {
		this.subCatList = subCatList;
	}
}
