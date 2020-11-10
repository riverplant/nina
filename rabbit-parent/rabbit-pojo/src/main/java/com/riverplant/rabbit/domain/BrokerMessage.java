package com.riverplant.rabbit.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.riverplant.rabbit.api.model.Message;

@Entity(name = "broker_message")
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "message_id" }) })
public class BrokerMessage extends DomainImpl {
	private static final long serialVersionUID = 3113833638677119445L;

	@Column(name = "message_id", nullable = false)
	private String messageId;
	
	@Column(name = "message", nullable = false)
	private Message message;

	//第一次发送不算，初始值设置为0
	@Column(name = "try_count", columnDefinition = "INT(4)")
	private int tryCount = 0;

	@Column(name = "status")
	private String status = "";
    
	/**
	 * 重试的关键字段
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "next_retry", nullable = false)
	private Date nextRetry = new Date();

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId == null ? null : messageId.trim();
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public int getTryCount() {
		return tryCount;
	}

	public void setTryCount(int tryCount) {
		this.tryCount = tryCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getNextRetry() {
		return nextRetry;
	}

	public void setNextRetry(Date nextRetry) {
		this.nextRetry = nextRetry;
	}

	public BrokerMessage() {
		
	}	
}
