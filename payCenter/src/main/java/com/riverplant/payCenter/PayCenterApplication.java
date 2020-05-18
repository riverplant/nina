package com.riverplant.payCenter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.riverplant.payCenter.config.WeixinPayConfig;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * payCenter
 *
 */
@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableSwagger2
@EnableConfigurationProperties(value = WeixinPayConfig.class)
public class PayCenterApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication application = new SpringApplication(PayCenterApplication.class);
    	application.run(args);
    }
}
