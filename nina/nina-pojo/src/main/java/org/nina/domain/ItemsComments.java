package org.nina.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;

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

	private Integer comment_level;

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

	public Integer getComment_level() {
		return comment_level;
	}

	public void setComment_level(Integer comment_level) {
		this.comment_level = comment_level;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
