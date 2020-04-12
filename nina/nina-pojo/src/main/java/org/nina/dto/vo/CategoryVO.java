package org.nina.dto.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 二级分类VO
 * 
 * @author jli
 *
 */
public class CategoryVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private Integer type;
	private Long fatherId;
	private List<SubCategoryVO> subCatList;

	
	public CategoryVO(Long id, String name, Integer type, Long fatherId, List<SubCategoryVO> subCatList) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.fatherId = fatherId;
		this.subCatList = subCatList;
	}

	
	public CategoryVO() {
		
	}


	public String getName() {
		return name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFatherId() {
		return fatherId;
	}

	public void setFatherId(Long fatherId) {
		this.fatherId = fatherId;
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

	public List<SubCategoryVO> getSubCatList() {
		return subCatList;
	}

	public void setSubCatList(List<SubCategoryVO> subCatList) {
		this.subCatList = subCatList;
	}
}
