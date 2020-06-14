package org.nina.dto.vo.center;

/**
 * 用户中心评论内容封装对象
 * 
 * @author riverplant
 *
 */
public class OrderItemsCommentVO {

	private Long id;
	private Long itemId;
	private String itemName;
	private Long itemSPecId;
	private String itemSpecName;
	private Integer commentLevel;
	private String content;

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

	public Long getItemSPecId() {
		return itemSPecId;
	}

	public void setItemSPecId(Long itemSPecId) {
		this.itemSPecId = itemSPecId;
	}

	public String getItemSpecName() {
		return itemSpecName;
	}

	public void setItemSpecName(String itemSpecName) {
		this.itemSpecName = itemSpecName;
	}

	public Integer getCommentLevel() {
		return commentLevel;
	}

	public void setCommentLevel(Integer commentLevel) {
		this.commentLevel = commentLevel;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
