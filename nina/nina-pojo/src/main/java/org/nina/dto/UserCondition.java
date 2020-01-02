package org.nina.dto;

import java.util.Date;
import org.nina.domain.Sex;

public class UserCondition {
	/**
	 * 用户名 用户名
	 */
	private String username;

	/**
	 * 年龄,在dto与前台交互的对象，应该使用Integer这样的包装类型
	 */
	private Integer age;
	
	private Integer ageTo;
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
	private Sex sex;

	/**
	 * 生日 生日
	 */
	private Date birthday;
	
	

	public Integer getAgeTo() {
		return ageTo;
	}

	public void setAgeTo(Integer ageTo) {
		this.ageTo = ageTo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
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
	
}
