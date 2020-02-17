/**
 * 
 */
package org.nina.security.Oauth2.Social.weixin.connect;

import org.nina.security.Oauth2.Social.weixin.api.Weixin;
import org.nina.security.Oauth2.Social.weixin.api.impl.WeixinTemplate;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * @author riverplant 向springsocial的connect体系提供微信这个对象的
 *         主要是通过OAuth2Template对象来帮助我们走完微信的OAuth流程
 *         为connect提供一个服务对象
 */
public class WeixinServiceProvider extends AbstractOAuth2ServiceProvider<Weixin> {

	/**
	 * 微信获取授权码的url
	 */
	private static final String URL_AUTHORIZE = "https://open.weixin.qq.com/connect/qrconnect";
	/**
	 * 微信获取accessToken的url
	 */
	private static final String URL_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";

	/**
	 * 在微信开发平台上注册应用的时候可以获得这两个参数
	 * 
	 * @param appid
	 * @param appSecret
	 */
	public WeixinServiceProvider(String appid, String appSecret) {
		super(getOAuth2Template(appid, appSecret));
	}
    /**
     * OAuth2Template:真正走微信OAuth流程的对象
     * @param appId
     * @param appSecret
     * @return
     */
	private static OAuth2Operations getOAuth2Template(String appId, String appSecret) {
		OAuth2Template oAuth2Template = new OAuth2Template(appId, appSecret, URL_AUTHORIZE,
				URL_ACCESS_TOKEN);
		//设置调取微信OAuth认证的时候使用参数的方式来传递参数(?...&...)
		oAuth2Template.setUseParametersForClientAuthentication(true);
		return oAuth2Template;
	}

	@Override
	public Weixin getApi(String accessToken) {
		return new WeixinTemplate(accessToken);
	}
}
