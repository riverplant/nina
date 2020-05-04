/**
 * 
 */
package org.nina.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author riverplant
 *
 */
@Entity(name = "orders")
public class Orders extends DomainImpl {
	private static final long serialVersionUID = 1L;
	// 用户id
	@Column(name = "user_id")
	Long userId;

	// 收货人快照
	@Column(name = "receiver_name")
	String receiverName;

	// 收货人手机快照
	@Column(name = "receiver_mobile")
	String receiverMobile;

	// 收货人地址快照
	@Column(name = "receiver_address")
	String receiverAddress;

	// 订单总价格
	@Column(name = "total_amount")
	int totalAmount;

	// 实际支付总价格
	@Column(name = "real_pay_amount")
	int realPayAmount;

	// 邮费
	@Column(name = "post_amount")
	int postAmount;

	// 支付方式
	@Column(name = "pay_method")
	int payMethod;

	// 买家留言
	@Column(name = "left_msg")
	String leftMsg;

	// 扩展字段
	@Column(name = "extand")
	String extand;

	// 买家是否评价
	@Column(name = "is_comment")
	int isComment;

	// 逻辑删除状态,不回真的删除
	@Column(name = "is_delete")
	int isDelete;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order")
	@JsonIgnore
	Set<OrderDetail> item = new HashSet<>();

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getRealPayAmount() {
		return realPayAmount;
	}

	public void setRealPayAmount(int realPayAmount) {
		this.realPayAmount = realPayAmount;
	}

	public int getPostAmount() {
		return postAmount;
	}

	public void setPostAmount(int postAmount) {
		this.postAmount = postAmount;
	}

	public int getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(int payMethod) {
		this.payMethod = payMethod;
	}

	public String getLeftMsg() {
		return leftMsg;
	}

	public void setLeftMsg(String leftMsg) {
		this.leftMsg = leftMsg;
	}

	public String getExtand() {
		return extand;
	}

	public void setExtand(String extand) {
		this.extand = extand;
	}

	public int getIsComment() {
		return isComment;
	}

	public void setIsComment(int isComment) {
		this.isComment = isComment;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public Orders() {
	}
}
