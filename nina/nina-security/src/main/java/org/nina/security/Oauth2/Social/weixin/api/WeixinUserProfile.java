package org.nina.security.Oauth2.Social.weixin.api;

import java.io.Serializable;

/**
 * 调用第三方服务后返回得到的用户信息封装类
 * 
 * @author riverplant
 *
 */
public class WeixinUserProfile implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 普通用户标识，对当前开发者账号唯一
	 */
	private String openid;
	/**
	 * 普通用户昵称
	 */
	private String nickname;
	/**
	 * 语言
	 */
	private String language;
	/**
	 * 普通用户性别:1为男，2为女
	 */
	private int sex;
	/**
	 * 普通用户各自人资料填写的城市
	 */
	private String city;
	/**
	 * 普通用户各自人资料填写的省份
	 */
	private String province;

	private String headImageUrl;

	public String getHeadImageUrl() {
		return headImageUrl;
	}

	public void setHeadImageUrl(String headImageUrl) {
		this.headImageUrl = headImageUrl;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public WeixinUserProfile() {
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
