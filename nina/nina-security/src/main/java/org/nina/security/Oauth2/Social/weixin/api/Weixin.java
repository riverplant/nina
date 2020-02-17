package org.nina.security.Oauth2.Social.weixin.api;
/**
 * 
 * @author riverplant
 * 最终要调用的第三方服务
 */
public interface Weixin {

	WeixinUserProfile getUserProfile(String openId);
}
