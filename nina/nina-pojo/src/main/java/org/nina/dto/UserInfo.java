package org.nina.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.nina.commons.enums.Sex;
import org.nina.domain.Address;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户对象", description = "用户对象,与前端交互")
public class UserInfo {
	public interface UserListView {
	};

	public interface UserDetailView extends UserListView {
	};

	@ApiModelProperty(value = "用户id", name = "id", example = "1L", required = false)
	private long id;
	/**
	 * 用户名 用户名
	 */
	@ApiModelProperty(value = "用户名", name = "username", example = "riverplant", required = true)
	@NotBlank
	private String username;

	/**
	 * 密码 密码
	 */
	@ApiModelProperty(value = "密码", name = "password", example = "123456", required = true)
	@NotBlank
	@Length(min = 6, message = "密码长度不正确!")
	private String password;

	/**
	 * 确认密码
	 */
	@ApiModelProperty(value = "确认密码", name = "confirmPassword", example = "123456", required = false)
	@NotBlank
	@Length(min = 6, message = "确认密码长度不正确!")
	private String confirmPassword;
	/**
	 * 年龄
	 */
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
	@Pattern(regexp = "", message = "")
	private String mobile;

	/**
	 * 邮箱地址 邮箱地址
	 */
	@Email
	private String email;

	/**
	 * 定义枚举值属性，保存为字符
	 */
	private Integer sex;

	/**
	 * 生日 生日
	 */
	private Date birthday;

	// @Embedded
	// private Address address;

	// 注解集合，会自动多生成一张表，形成一对多关系
	private List<String> hobbies;

	private List<Address> addresses;

	@JsonView(UserListView.class)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonView(UserListView.class)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonView(UserDetailView.class)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonView(UserDetailView.class)
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@JsonView(UserDetailView.class)
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@JsonView(UserDetailView.class)
	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	@JsonView(UserListView.class)
	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	@JsonView(UserListView.class)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@JsonView(UserListView.class)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@JsonView(UserDetailView.class)
	public Date getBirthday() {
		return birthday;
	}

	@JsonView(UserDetailView.class)
	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	@JsonView(UserDetailView.class)
	public List<String> getHobbies() {
		return hobbies;
	}

	public void setHobbies(List<String> hobbies) {
		this.hobbies = hobbies;
	}

	@JsonView(UserDetailView.class)
	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
