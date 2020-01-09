package org.nina;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.nina.api.config.TestProperties;
import org.nina.repository.support.NinaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = NinaRepositoryImpl.class)
@EnableCaching
//使用该注解，读取自己在配置文件中设置的配置项的配置类生效
@EnableConfigurationProperties(value = TestProperties.class)
@EnableScheduling
//扫描所有包以及相关组件包
//@ComponentScan(basePackages = {"",""})
//引入配置文件@ImportResource("classpath:consumer.xml")
public class Application 
{
    public static void main( String[] args )
    {
    	SpringApplication application = new SpringApplication(Application.class);
    	//激活日志配置，开启控制台打印
    	application.setAdditionalProfiles("dev");
    	application.run(args);
    }
    
    /**
     * 设置redis缓存时间
     * @return
     */
//    @Bean
//    public CacheManagerCustomizer<RedisCacheManager> cacheCustomizer() {
//    	
//    	return new CacheManagerCustomizer<RedisCacheManager>() {
//
//			@Override
//			public void customize(RedisCacheManager cacheManager) {
//				 RedisCacheConfiguration redisCacheConfiguration = 
//						    RedisCacheConfiguration.defaultCacheConfig()//生成一个默认配置，通过config对象即可对缓存进行自定义配置
//			                .entryTtl(Duration.ofMinutes(10))// 设置缓存有效期十分钟
//			                .disableCachingNullValues(); // 不缓存空值
//				 Set<String> cacheName = new HashSet<>();//设置一个初始化的缓存空间set集合
//				 cacheName.add("items");
//				// 对每个缓存空间应用不同的配置
//				 Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
//				 configMap.put("items", redisCacheConfiguration);
//				 //configMap.put("items", redisCacheConfiguration.entryTtl(Duration.ofSeconds(120)));
//				cacheManager.set
//				RedisCacheManager.se
//			}
//    		
//    	};
//    }
    
   /**
    * 自定义缓存管理器.相当于缓存的配置文件
    * @param redisConnectionFactory
    * @return
    */
	@Bean()  
	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		 RedisCacheConfiguration redisCacheConfiguration = 
				    RedisCacheConfiguration.defaultCacheConfig()//生成一个默认配置，通过config对象即可对缓存进行自定义配置
	                .entryTtl(Duration.ofMinutes(10))// 设置缓存有效期十分钟
	                .disableCachingNullValues(); // 不缓存空值

		 Set<String> cacheName = new HashSet<>();//设置一个初始化的缓存空间set集合
		 cacheName.add("items");
		 
		// 对每个缓存空间应用不同的配置
		 Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
		 //configMap.put("items", redisCacheConfiguration);
		 configMap.put("items", redisCacheConfiguration.entryTtl(Duration.ofSeconds(120)));
		 
		RedisCacheManager redisCacheManager  =   
				RedisCacheManager.builder(redisConnectionFactory)//使用自定义的缓存配置初始化一个cacheManager
				// 注意这两句的调用顺序，一定要先调用该方法设置初始化的缓存名，再初始化相关的配置
				.initialCacheNames(cacheName)
                .withInitialCacheConfigurations(configMap)
				.build();
		
		return redisCacheManager;
    }	
}
