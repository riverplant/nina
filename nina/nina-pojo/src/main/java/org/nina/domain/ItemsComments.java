package org.nina.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;

import org.nina.commons.enums.CommentLevel;

/**
 * 商品评价
 * 
 * @author jli
 *
 */
@Entity
@NamedEntityGraph(name = "ItemsComments.fetch.User.and.Items", attributeNodes = { @NamedAttributeNode("user"),
		@NamedAttributeNode("items") })
public class ItemsComments extends DomainImpl {

	private static final long serialVersionUID = 1L;

	/**
	 * 尽可能用外键来维护多对一的关系
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	/**
	 * 尽可能用外键来维护多对一的关系
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Items items;

	@Column(name = "item_name")
	private String itemName;
	/**
	 * 因为商品会有多种规格，所以需要记录规格id
	 */
	@Column(name = "item_spec_id")
	private Long itemSpecId;

	@Enumerated(EnumType.STRING)
	private CommentLevel commentLevel;

	@Column(name = "content")
	private String content;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Items getItems() {
		return items;
	}

	public void setItems(Items items) {
		this.items = items;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Long getItemSpecId() {
		return itemSpecId;
	}

	public void setItemSpecId(Long itemSpecId) {
		this.itemSpecId = itemSpecId;
	}

	public CommentLevel getCommentLevel() {
		return commentLevel;
	}

	public void setCommentLevel(CommentLevel commentLevel) {
		this.commentLevel = commentLevel;
	}

}
