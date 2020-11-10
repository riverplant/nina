package org.nina.distribute.lock.redis;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.core.types.Expiration;

/**
 * 封装redis分布式锁
 * 
 * @author riverplant
 *
 */
public class RedisLock implements AutoCloseable{
	private RedisTemplate redisTemplate;
	// key,由前端传入业务代码
	private String key;
	// value，必须唯一性，用于删除key的时候验证
	private String value;
	// 锁的过期时间，单位秒
	private int expiraTime;
	private static Logger log = LoggerFactory.getLogger(RedisLock.class);
	public RedisLock(RedisTemplate redisTemplate, String key, int expiraTime) {
		this.redisTemplate = redisTemplate;
		this.key = key;
		this.expiraTime = expiraTime;
		this.value = UUID.randomUUID().toString();
	}

	/**
	 * 获取分布式锁的方法
	 * 
	 * @return
	 */
	public boolean getLock() {
		RedisCallback<Boolean> redisCallback = connection -> {
			// NX: key不存在的时候设置成功，key存在则设置不成功【原子性操作】,设置成功即获得锁，可以执行后续的业务
			RedisStringCommands.SetOption setOption = RedisStringCommands.SetOption.ifAbsent();
			// PX: 给key设置自动失效时间，当服务释放锁的时候出现异常情况，锁可以过期失效
			Expiration expiration = Expiration.seconds(this.expiraTime);
			// 使用redisTemplate自带方法对key和value进行序列化
			byte[] redisKey = redisTemplate.getKeySerializer().serialize(key);
			byte[] redisvalue = redisTemplate.getKeySerializer().serialize(value);
			return connection.set(redisKey, redisvalue, expiration, setOption);
		};
		Boolean lock = (Boolean) redisTemplate.execute(redisCallback);
		return lock;
	}

	/**
	 * 释放分布式锁
	 * 
	 * @return
	 */
	public boolean unLock() {
		/**
		 * 释放锁使用LUA脚本
		 */
		String script = " if redis.call(\"get\",KEY[1]) == ARGV[1] then\r\n"
				+ "         return redis.call(\"del\",KEYS[1])\r\n" + "         else \r\n" + "            return 0\r\n"
				+ "         end";
		RedisScript<Boolean> redisScript = RedisScript.of(script, Boolean.class);
		List keys = Arrays.asList(key);
		Boolean result = (Boolean) redisTemplate.execute(redisScript, keys, value);
		return result;
	}

	@Override
	public void close() throws Exception {
		Boolean result = unLock();
		log.info("释放锁的结果" + result);
	}
}
