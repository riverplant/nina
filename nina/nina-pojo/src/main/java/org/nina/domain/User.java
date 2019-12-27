package org.nina.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class User extends DomainImpl {
	private static final long serialVersionUID = 3113833638677119445L;

	/**
	 * 用户名 用户名
	 */
	private String username;

	/**
	 * 密码 密码
	 */
	private String password;
	/**
	 * 年龄
	 */
	@Column(columnDefinition = "INT(3)")
	private int age;
	/**
	 * 昵称 昵称
	 */
	private String nickname;

	/**
	 * 真实姓名
	 */
	private String realname;

	/**
	 * 头像
	 */
	private String face;

	/**
	 * 手机号 手机号
	 */
	private String mobile;

	/**
	 * 邮箱地址 邮箱地址
	 */
	private String email;

	/**
	 * 定义枚举值属性，保存为字符
	 */
	@Enumerated(EnumType.STRING)
	private Sex sex;

	/**
	 * 生日 生日
	 */
	@Temporal(TemporalType.DATE)
	private Date birthday;

	// @Embedded
	// private Address address;

	// 注解集合，会自动多生成一张表，形成一对多关系
	@ElementCollection
	private List<String> hobbies;

	@ElementCollection
	private List<Address> addresses;

	public List<String> getHobbies() {
		return hobbies;
	}

	public void setHobbies(List<String> hobbies) {
		this.hobbies = hobbies;
	}

	public String getUsername() {
		return username;
	}

	// public Address getAddress() {
	// return address;
	// }
	//
	// public void setAddress(Address address) {
	// this.address = address;
	// }

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
