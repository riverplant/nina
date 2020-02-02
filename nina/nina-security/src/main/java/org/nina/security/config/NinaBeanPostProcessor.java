package org.nina.security.config;

import org.nina.security.Oauth2.NinaAuthoritiesExtractor;
import org.nina.security.Oauth2.NinaPrincipalExtractor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.stereotype.Component;
@Component
public class NinaBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		/**
		 * 对OAuth认证后返回的信息进行封装成authentication
		 * 1.修改FixedPrincipalExtractor的调用UserInfoTokenService,加入需要获取的值对应的key
		 * 2.UserInfoTokenServices将获取的信息转成OAUth2Authentication
		 */
		if(bean instanceof UserInfoTokenServices) {
			//可以放入自己定义的AuthoritiesExtractor取代默认的FixedAuthoritiesExtractor
			((UserInfoTokenServices) bean).setAuthoritiesExtractor(new NinaAuthoritiesExtractor());
			//可以放入自己定义的PrincipalExtractor取代默认的FixedPrincipalExtractor
			((UserInfoTokenServices) bean).setPrincipalExtractor(new NinaPrincipalExtractor());
			System.out.println(beanName);
		}
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
	}

	
}
