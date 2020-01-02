package org.nina.service;

import org.nina.repository.support.NinaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = NinaRepositoryImpl.class,basePackages = "org.nina.repository")
//@ImportResource("classpath:provider.xml")
@EnableCaching
@EnableScheduling
@EntityScan("org.nina.domain")
public class ServiceApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(ServiceApplication.class, args);
    }
}