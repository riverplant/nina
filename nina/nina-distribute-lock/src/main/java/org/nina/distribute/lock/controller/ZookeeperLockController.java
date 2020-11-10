package org.nina.distribute.lock.controller;

import java.util.UUID;

import org.nina.distribute.lock.zookeeper.ZKLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ZookeeperLockController {
	private static Logger log = LoggerFactory.getLogger(ZookeeperLockController.class);
	// key,由前端传入业务代码
	String key = "redisKey";
	// value，必须唯一性，用于删除key的时候验证
	String value = UUID.randomUUID().toString();

	@RequestMapping("zklock")
	public String zookeeperLock() {
		log.info("我进入了方法");
        //自动释放
		try (ZKLock ZKLock = new ZKLock()) {
			if (ZKLock.getLock("order")) {
				log.info("我获得了锁");
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
