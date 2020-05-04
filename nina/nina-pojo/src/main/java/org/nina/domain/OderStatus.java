/**
 * 
 */
package org.nina.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author riverplant 订单状态表
 */
@Entity(name = "order_status")
public class OderStatus extends DomainImpl {
	private static final long serialVersionUID = 1L;

	// 订单id
	@Column(name = "order_id")
	Long orderId;

	/**
	 * 订单状态:
	 * 10:代付款
	 * 20：已付款 代发货
	 * 30：已发货，待收货（七天自动确认）
	 * 40：交易成功（此时可以评价）
	 * 50：交易关闭（待付款时可选）待付款时用户取消或者长时间未付款
	 *     系统可以自动关闭
	 */
	@Column(name = "order_status")
	Integer orderStatus;

	// 支付成功时间
	@Column(name = "pay_time")
	Date payTime;

	// 发货时间
	@Column(name = "deliver_time")
	Date deliverTime;

	// 交易成功时间
	@Column(name = "success_time")
	Date successTime;

	// 交易关闭时间
	@Column(name = "close_time")
	Date closeTime;

	// 留言时间
	@Column(name = "comment_time")
	Date commentTime;

	public Date getDeliverTime() {
		return deliverTime;
	}

	public void setDeliverTime(Date deliverTime) {
		this.deliverTime = deliverTime;
	}

	public Date getSuccessTime() {
		return successTime;
	}

	public void setSuccessTime(Date successTime) {
		this.successTime = successTime;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public OderStatus(Integer orderStatus, Date payTime) {
		this.orderStatus = orderStatus;
		this.payTime = payTime;
	}

	public OderStatus() {

	}
}
