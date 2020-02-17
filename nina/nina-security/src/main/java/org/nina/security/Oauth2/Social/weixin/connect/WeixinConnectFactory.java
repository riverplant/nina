package org.nina.security.Oauth2.Social.weixin.connect;

import org.nina.security.Oauth2.Social.weixin.api.Weixin;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * 核心类
 * 
 * @author riverplant
 *
 */
public class WeixinConnectFactory extends OAuth2ConnectionFactory<Weixin> {

	public WeixinConnectFactory(String providerId, String appId, String appSecret) {
		super(providerId, new WeixinServiceProvider(appId, appSecret), new WeixinAdapter());

	}

}
