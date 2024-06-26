package org.nina.dto.vo;

import java.util.List;

/**
 * 
 * @author jli 最新商品VO
 */
public class NewItemsVO {

	private Long rootCatId;
	private String rootCatName;
	private String slogan;
	private String catImage;
	private String bgColor;

	List<SimpleItemVO> simpleItemList;

	public Long getRootCatId() {
		return rootCatId;
	}

	public void setRootCatId(Long rootCatId) {
		this.rootCatId = rootCatId;
	}

	public String getRootCatName() {
		return rootCatName;
	}

	public void setRootCatName(String rootCatName) {
		this.rootCatName = rootCatName;
	}

	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public String getCatImage() {
		return catImage;
	}

	public void setCatImage(String catImage) {
		this.catImage = catImage;
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public List<SimpleItemVO> getSimpleItemList() {
		return simpleItemList;
	}

	public void setSimpleItemList(List<SimpleItemVO> simpleItemList) {
		this.simpleItemList = simpleItemList;
	}

}
