package org.nina.distribute.lock.controller;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.nina.distribute.lock.redis.RedisLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisLockController {
	private static Logger log = LoggerFactory.getLogger(RedisLockController.class);
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private RedissonClient redisson;
	// key,由前端传入业务代码
	String key = "redisKey";
	// value，必须唯一性，用于删除key的时候验证
	String value = UUID.randomUUID().toString();

	@RequestMapping("redisLock")
	public String redisLock() {
		log.info("我进入了方法");
		// 自动释放
		try (RedisLock redisLock = new RedisLock(redisTemplate, "redisKey", 30)) {
			if (redisLock.getLock()) {
				log.info("我进入了锁");
				/**
				 * 模拟业务执行
				 */
				Thread.sleep(15000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		log.info("方法执行完成");
		return "";
	}

	@RequestMapping("redissonLock")
	public String redissonLock() {
		log.info("我进入了方法");
		RLock rLock = redisson.getLock("order");
		try {
			// 设置锁的过期时间，当过期没有释放锁，会自动释放
			rLock.lock(30, TimeUnit.SECONDS);
			//模拟业务
			Thread.sleep(10000);
			log.info("我获得了锁");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			rLock.unlock();
			log.info("我釋放了锁");
		}

		log.info("方法执行完成");
		return "";
	}
}
