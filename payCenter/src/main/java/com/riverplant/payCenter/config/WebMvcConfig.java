package com.riverplant.payCenter.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		
		return builder.build();
	}
    /**
     * 实现静态资源的映射，把响应的地址进行注册
     */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String os = System.getProperty("os.name");
		if(os.toLowerCase().startsWith("win")) {//windows系统
			registry.addResourceHandler("/**")
			/**
			 * 映射本地静态资源,浏览器端通过localhost:8080/{写映射文件路径之后的子路径}nina/faces/*.jpg可以访问头像图片
			 */
	        .addResourceLocations("file:J:\\riverplant\\images\\")
	        .addResourceLocations("classpath:/META-INF/resources/");//为swagger2进行映射	
		}else {//linux 或者是mac
			registry.addResourceHandler("/**")
	        .addResourceLocations("file:/riverplant/images/")
	        .addResourceLocations("classpath:/META-INF/resources/");//为swagger2进行映射
		}	
	}
	
	
}
