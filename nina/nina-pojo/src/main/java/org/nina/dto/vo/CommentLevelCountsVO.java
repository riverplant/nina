package org.nina.dto.vo;

/**
 * 用于展示商品评价数的VO
 * 
 * @author jli
 *
 */
public class CommentLevelCountsVO {

	private Integer totalCount;
	private Integer goodCount;
	private Integer normalCount;
	private Integer badCount;

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount() {
		this.totalCount = this.badCount+this.goodCount+this.normalCount;
	}

	public Integer getGoodCount() {
		return goodCount;
	}

	public void setGoodCount(Integer goodCount) {
		this.goodCount = goodCount;
	}

	public Integer getNormalCount() {
		return normalCount;
	}

	public void setNormalCount(Integer normalCount) {
		this.normalCount = normalCount;
	}

	public Integer getBadCount() {
		return badCount;
	}

	public void setBadCount(Integer badCount) {
		this.badCount = badCount;
	}

}
