package org.nina.api.config;

import java.util.ArrayList;
import java.util.List;

import org.nina.api.interceptor.NinaInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ch.qos.logback.core.net.server.ServerListener;

/**
 * 
 * @author riverplant
 *
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	NinaInterceptor ninaInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(ninaInterceptor);
	}
    /**
     * 注册CharacterEncodingFilter实现转码,在intercepter之前执行
     * @return
     */
	@Bean
	public FilterRegistrationBean<CharacterEncodingFilter> CharacterEncodingFilterRegister() {
        //ServletRegistrationBean;注册servlet
        //ServletListenerRegistrationBean<EventListener> :注册Listener
		FilterRegistrationBean<CharacterEncodingFilter> registrationBean = new FilterRegistrationBean<>();
		CharacterEncodingFilter filter = new CharacterEncodingFilter("UTF-8");
		filter.setForceEncoding(true);
		registrationBean.setFilter(filter);
		List<String> urls = new ArrayList<>();
		urls.add("/*");
		registrationBean.setUrlPatterns(urls);
		return registrationBean;
	}
}
