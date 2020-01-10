package org.nina.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
/**
 * 
 * @author jli
 * 设置跨域
 */
@Configuration
public class CorsConfig {

	public CorsConfig() {
	
	}
    @Bean
	public CorsFilter corsFilter() {
		//1.添加Cors配置信息
    	CorsConfiguration config = new CorsConfiguration();
    	config.addAllowedOrigin("http://localhost:8080");
    	//设置是否发送Cookie 
    	config.setAllowCredentials(true);
    	//设置请求的方式
    	config.addAllowedMethod("*");
    	//设置head的方式
    	config.addAllowedHeader("*");
    	
    	
    	//2.为url添加映射路径
    	UrlBasedCorsConfigurationSource corseSource = new UrlBasedCorsConfigurationSource();
    	corseSource.registerCorsConfiguration("/**", config);
    	
    	//3.return
    	return new CorsFilter(corseSource);
	}
}
