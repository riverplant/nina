package org.nina.security.Oauth2.Social.weixin.api.impl;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.nina.security.Oauth2.Social.weixin.api.Weixin;
import org.nina.security.Oauth2.Social.weixin.api.WeixinUserProfile;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 微信接口的实现
 * @author riverplant
 *AbstractOAuth2ApiBinding:用来维护accessToken和restTemplate,在发送rest请求的时候会自动带上accessToken
 *                            
 */
public class WeixinTemplate extends AbstractOAuth2ApiBinding implements Weixin{
    private ObjectMapper objectMapper = new ObjectMapper();
	private static final String URL_GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?openid=";
	
	/**
	 * 将accessToken传给父类
	 * @param accessToken
	 */
	public WeixinTemplate(String accessToken) {
		//ACCESS_TOKEN_PARAMETER:每次发请求的时候将accessToken作为参数挂在请求上
		super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
	}


	@Override
	public WeixinUserProfile getUserProfile(String openId) {
		String url = URL_GET_USER_INFO + openId;
		String response = getRestTemplate().getForObject(url,String.class);
		WeixinUserProfile profile = null;
		try {
			profile = objectMapper.readValue(response, WeixinUserProfile.class);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return profile;
	}
	/**
	 * 默认注入的StringHttpMessageConverter字符集为ISO-8859-1
	 * 这里覆盖成UTF-8来转换Content-text
	 */
	protected List<HttpMessageConverter<?>> getMessageConverters() {
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		messageConverters.add(getFormMessageConverter());
		messageConverters.add(getJsonMessageConverter());
		messageConverters.add(getByteArrayMessageConverter());
		return messageConverters;
	}

}
