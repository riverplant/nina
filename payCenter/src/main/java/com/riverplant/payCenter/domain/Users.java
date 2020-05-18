package com.riverplant.payCenter.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Users {
	    @Id
	    private Integer id;

	    /**
	     * 用户在慕课网的用户id
	     */
	    @Column(name = "imooc_user_id")
	    private String imoocUserId;

	    /**
	     * 由我分配给用户的密码，这个密码存入数据库需要加密
	     */
	    private String password;

	    /**
	     * 用户访问有效期，20元/月
	     */
	    @Column(name = "end_date")
	    private Date endDate;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getImoocUserId() {
			return imoocUserId;
		}

		public void setImoocUserId(String imoocUserId) {
			this.imoocUserId = imoocUserId;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public Date getEndDate() {
			return endDate;
		}

		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}
}
