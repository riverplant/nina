package org.nina.dto.vo;

/**
 * 6个最新商品的简单类型
 * 
 * @author jli
 *
 */
public class SimpleItemVO {

	private Long itemId;
	private String itemName;
	private String url;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
