package org.nina.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;

/**
 * 
 * @author riverplant
 * @Inheritance(strategy = InheritanceType.SINGLE_TABLE) 当有实体类继承该类，将不会创建新表
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
/**
 * 设置抓取信息，在查询Items的时候将相关联的category 和carousels信息一并抓取
 *
 */
@NamedEntityGraph(name = "Items.fetch.category.and.carousels.and.spec", attributeNodes = {
		@NamedAttributeNode("category"), @NamedAttributeNode("carousels"), @NamedAttributeNode("spec") })
public class Items extends DomainImpl {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品名称 商品名称
	 */
	@Column(name = "item_name")
	private String itemName;
	/**
	 * 乐观锁
	 */
	@Version
	private int version;

	/**
	 * 尽可能用外键来维护多对一的关系
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cat_id")
	private Category category;

	@Column(name = "root_cat_id")
	private Long rootCatId;
	/**
	 * 累计销售 累计销售
	 */
	@Column(name = "sell_counts")
	private Integer sellCounts;

	/**
	 * 上下架状态 上下架状态,1:上架 2:下架
	 */
	@Column(name = "on_off_status")
	private Integer onOffStatus;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "items")
	private Set<ItemsCarouselAssociation> carousels;

	/**
	 * 商品内容
	 */
	@Column(name = "content")
	private String content;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "items")
	private Set<ItemsSpec> spec;
	/**
	 * 商品图片
	 */
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "items")
	private Set<Items_img> itemsImg;

	@OneToOne(mappedBy = "items")
	private ItemsParam itemsParam;
	/**
	 * 商品评价
	 */
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "items")
	private Set<ItemsComments> itemsComments;

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Set<ItemsSpec> getSpec() {
		return spec;
	}

	public void setSpec(Set<ItemsSpec> spec) {
		this.spec = spec;
	}

	public ItemsParam getItemsParam() {
		return itemsParam;
	}

	public void setItemsParam(ItemsParam itemsParam) {
		this.itemsParam = itemsParam;
	}

	public Set<ItemsComments> getItemsComments() {
		return itemsComments;
	}

	public void setItemsComments(Set<ItemsComments> itemsComments) {
		this.itemsComments = itemsComments;
	}

	public Set<ItemsCarouselAssociation> getCarousels() {
		return carousels;
	}

	public void setCarousels(Set<ItemsCarouselAssociation> carousels) {
		this.carousels = carousels;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Integer getSellCounts() {
		return sellCounts;
	}

	public void setSellCounts(Integer sellCounts) {
		this.sellCounts = sellCounts;
	}

	public Integer getOnOffStatus() {
		return onOffStatus;
	}

	public void setOnOffStatus(Integer onOffStatus) {
		this.onOffStatus = onOffStatus;
	}

	public Long getRootCatId() {
		return rootCatId;
	}

	public void setRootCatId(Long rootCatId) {
		this.rootCatId = rootCatId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Set<Items_img> getItemsImg() {
		return itemsImg;
	}

	public void setItemsImg(Set<Items_img> itemsImg) {
		this.itemsImg = itemsImg;
	}

}
