package org.nina.security.Oauth2.Social.weixin.connect;

import org.nina.security.Oauth2.Social.weixin.api.Weixin;
import org.nina.security.Oauth2.Social.weixin.api.WeixinUserProfile;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;

/**
 * 
 * @author riverplant
 * 主要是提供将调取第三方服务获得的关联对象名称和关联对象头像的属性字段
 * 映射成spring social中自定的displayName字段和imageUrl字段
 * 该映射关系在该类中定义
 */
public class WeixinAdapter implements ApiAdapter<Weixin>{

	private String openId;
	 
	public WeixinAdapter(String openId) {
		this.openId = openId;
	}

	public WeixinAdapter() {
	}
    /**
     * 判断将要获取的服务是否可用
     */
	@Override
	public boolean test(Weixin api) {
		/**
		 * 一般在这里会尝试调取一下服务作为验证
		 */
		return true;
	}
    /**
     * 将获取到的微信上的信息，设置到connection上
     */
	@Override
	public void setConnectionValues(Weixin api, ConnectionValues values) {
		WeixinUserProfile profile = api.getUserProfile(openId);
		values.setProviderUserId(profile.getOpenid());
		values.setDisplayName(profile.getNickname());
		values.setImageUrl(profile.getHeadImageUrl());
	}
    /**
     * 把第三方的用户信息转换成spring social标准的UserProfile
     */
	@Override
	public UserProfile fetchUserProfile(Weixin api) {
		WeixinUserProfile profile = api.getUserProfile(openId);
		return new UserProfileBuilder().setName(profile.getNickname()).build();
	}
    /**
     * 
     */
	@Override
	public void updateStatus(Weixin api, String message) {
		
	}

}
