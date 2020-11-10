package org.nina.distribute.lock.service;

import org.nina.distribute.lock.redis.RedisLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService {
	private static Logger log = LoggerFactory.getLogger(SchedulerService.class);
	@Autowired
	private RedisTemplate redisTemplate;

	// @Scheduled(cron="")定时发送
	public void sendSms() {
		// 获取分布式锁
		try (RedisLock redisLock = new RedisLock(redisTemplate, "aotuSms", 30)) {
			if (redisLock.getLock()) {
				// 获得锁之后
				log.info("发送短信.....");
				Thread.sleep(15000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

}
