package org.nina.api.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * @author riverplant 用来读取propertie文件自己创建的配置项
 */
//@ConfigurationProperties(prefix = "xxx")
public class TestProperties {

	private String h;
	private String k;
	private String m;
	
	private Map<String,String>users = new HashMap<>();

	public String getH() {
		return h;
	}

	public void setH(String h) {
		this.h = h;
	}

	public String getK() {
		return k;
	}

	public void setK(String k) {
		this.k = k;
	}

	public String getM() {
		return m;
	}

	public void setM(String m) {
		this.m = m;
	}

	public Map<String, String> getUsers() {
		return users;
	}

	public void setUsers(Map<String, String> users) {
		this.users = users;
	}

}
