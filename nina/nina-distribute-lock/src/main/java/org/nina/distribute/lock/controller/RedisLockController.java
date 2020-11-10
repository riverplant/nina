package org.nina.distribute.lock.controller;

import java.util.UUID;

import org.nina.distribute.lock.redis.RedisLock;
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
	// key,由前端传入业务代码
	String key = "redisKey";
	// value，必须唯一性，用于删除key的时候验证
	String value = UUID.randomUUID().toString();

	@RequestMapping("redisLock")
	public String redisLock() {
		log.info("我进入了方法");
        //自动释放
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
}
