package org.nina;

import org.nina.repository.support.NinaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = NinaRepositoryImpl.class)
public class Application 
{
    public static void main( String[] args )
    {
    	SpringApplication application = new SpringApplication(Application.class);
    	//激活日志配置，开启控制台打印
    	application.setAdditionalProfiles("dev");
    	application.run(args);
    }
}
