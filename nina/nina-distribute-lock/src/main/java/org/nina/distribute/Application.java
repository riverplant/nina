package org.nina.distribute;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
@SpringBootApplication
@EnableCaching
@EnableScheduling//开启定时任务
@EnableSwagger2
public class Application 
{
	@Value("${riverplant.zookeeper.connectString}")
	private String zookeeperConnectString;
	
    public static void main( String[] args )
    {
    	SpringApplication application = new SpringApplication(Application.class);
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
	/**
	 * 初始化调用start
	 * 结束调用close
	 * @return
	 */
	@Bean(initMethod="start",destroyMethod="close")
	@ConditionalOnMissingBean
	public CuratorFramework getCuratorFramework() {
		//获取连接
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3);
		CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeperConnectString, retryPolicy);
		return client;
	}
	
	/**
	 * 初始化调用start
	 * 结束调用close
	 * @return
	 */
//	@Bean(destroyMethod="shutdown")
//	@ConditionalOnMissingBean
//	public RedissonClient getRedisson() {
//		Config config = new Config();
//		//使用集群模式
//		config.useClusterServers()
//		.setScanInterval(2000) //集群状态扫描间隔时间
//		      .addNodeAddress("redis://127.0.0.1:6379");
//		return Redisson.create(config);
//	}
}
