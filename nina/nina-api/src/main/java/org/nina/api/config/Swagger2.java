package org.nina.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置类
 * @author jli
 *
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
	 /**
	 *http://localhost:8080/swagger-ui.html  :访问路径
	 * @return
	 */

	//配置Swagger2核心配置 docket
	@Bean
	public Docket createRestApi() {
		
		return new Docket(DocumentationType.SWAGGER_2)//指定API风格
				.apiInfo(apiInfo())//用于定义文档汇总信息
				.select()
				.apis(RequestHandlerSelectors
						.basePackage("org.nina.api.controller")) //指定Controller包
				.paths(PathSelectors.any())//指定Controller包下所有controller类
				.build();
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Nina魔法接口API")
				.contact(new Contact("Nina","","riverplant@hotmail.com"))
				.version("1.0.0")
				.description("Nina魔法接口API")
				.build();			
	}
}
