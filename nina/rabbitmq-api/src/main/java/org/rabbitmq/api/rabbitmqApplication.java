package org.rabbitmq.api;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableSwagger2
@EnableCaching
public class rabbitmqApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication application = new SpringApplication(rabbitmqApplication.class);
    	application.run(args);
    }
}
