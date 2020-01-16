package org.nina.dto.vo;

import java.util.List;

import org.nina.dto.ItemsInfo;
import org.nina.dto.ItemsParamInfo;
import org.nina.dto.ItemsSpecInfo;
import org.nina.dto.Items_imgInfo;

/**
 * 
 * 商品详情VO
 * @author jli
 *
 */
public class ItemInfosVO {

	private ItemsInfo itemsInfo;
	private List<Items_imgInfo> itemsImgInfosList;
	private ItemsParamInfo itemsParamInfo;
	private List<ItemsSpecInfo> itemsSpecInfosList;

	public ItemsInfo getItemsInfo() {
		return itemsInfo;
	}

	public void setItemsInfo(ItemsInfo itemsInfo) {
		this.itemsInfo = itemsInfo;
	}

	public List<Items_imgInfo> getItemsImgInfosList() {
		return itemsImgInfosList;
	}

	public void setItemsImgInfosList(List<Items_imgInfo> itemsImgInfosList) {
		this.itemsImgInfosList = itemsImgInfosList;
	}

	public ItemsParamInfo getItemsParamInfo() {
		return itemsParamInfo;
	}

	public void setItemsParamInfo(ItemsParamInfo itemsParamInfo) {
		this.itemsParamInfo = itemsParamInfo;
	}

	public List<ItemsSpecInfo> getItemsSpecInfosList() {
		return itemsSpecInfosList;
	}

	public void setItemsSpecInfosList(List<ItemsSpecInfo> itemsSpecInfosList) {
		this.itemsSpecInfosList = itemsSpecInfosList;
	}

}
