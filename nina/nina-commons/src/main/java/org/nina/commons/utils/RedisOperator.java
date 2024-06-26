package org.nina.commons.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Redis工具类
 * 
 * @author riverplant 将所有要存储的对象都序列化为string,然后进行存储
 */
@Component
public class RedisOperator {
	// 使用StringRedisTemplate可以防止乱码
	@Autowired
	private StringRedisTemplate redisTemplate;

	/**
	 * TTL key, 以秒为单位，返回给定key的过期时间
	 * 
	 * @param key
	 * @return
	 */
	public Long ttl(String key) {
		return redisTemplate.getExpire(key);
	}

	/**
	 * 设置过期时间
	 * 
	 * @param key
	 * @param time
	 */
	public void expire(String key, long time) {
		redisTemplate.expire(key, time, TimeUnit.SECONDS);
	}

	/**
	 * INCR key: 增加key
	 * 
	 * @param key
	 * @param delta
	 * @return
	 */
	public long incr(String key, long delta) {
		return redisTemplate.opsForValue().increment(key, delta);
	}

	/**
	 * 查询所有符合给定模式
	 * 
	 * @param pattern:匹配模式
	 * @return
	 */
	public Set<String> keys(String pattern) {
		return redisTemplate.keys(pattern);
	}

	/**
	 * 实现DEL key
	 * 
	 * @param key
	 */
	public void del(String key) {
		redisTemplate.delete(key);
	}

	/**
	 * SET key value
	 * 
	 * @param key
	 * @param val
	 */
	public void set(String key, String val) {
		redisTemplate.opsForValue().set(key, val);
	}

	/**
	 * SET key value EX seconds
	 * 
	 * @param key
	 * @param val
	 * @param timeout
	 */
	public void set(String key, String val, long timeout) {
		redisTemplate.opsForValue().set(key, val, timeout, TimeUnit.SECONDS);
	}

	/**
	 * GET key
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	/**
	 * MGET 批量查询
	 * 
	 * @param keys
	 * @return
	 */
	public List<String> mget(List<String> keys) {
		return redisTemplate.opsForValue().multiGet(keys);
	}

	/**
	 * 通过管道 批量查询
	 * 
	 * @param keys
	 * @return
	 */
	public List<Object> batchget(String... keys) {
		// nginx -> keepalive
		// redis -> pipeline : 只建立一次连接
		List<Object> result = redisTemplate.executePipelined(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection src = (StringRedisConnection) connection;
				src.mGet(keys);
				return null;
			}
		});
		return result;
	}

	// Hash
	/**
	 * HSET key field value
	 * 
	 * @param key
	 * @param field
	 * @param value
	 */
	public void hset(String key, String field, String value) {
		redisTemplate.opsForHash().put(key, field, value);
	}

	/**
	 * HGET key field
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public String hget(String key, String field) {
		return (String) redisTemplate.opsForHash().get(key, field);
	}

	/**
	 * 
	 * @param key
	 * @param fields
	 */
	public void hdel(String key, Object... fields) {
		redisTemplate.opsForHash().delete(key, fields);
	}

	/**
	 * HGETALL key 返回哈希表中key的所有域和值
	 * 
	 * @param key
	 * @return
	 */
	public Map<Object, Object> hgetall(String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	/**
	 * LPUSH key value
	 * 
	 * @param key
	 * @param val
	 * @return
	 */
	public long lpush(String key, String val) {
		return redisTemplate.opsForList().leftPush(key, val);
	}

	/**
	 * LPOP
	 * 
	 * @param key
	 * @return
	 */
	public String lpop(String key) {
		return redisTemplate.opsForList().leftPop(key);
	}

	/**
	 * RPUSH key value
	 * 
	 * @param key
	 * @param val
	 * @return
	 */
	public long ppush(String key, String val) {
		return redisTemplate.opsForList().rightPush(key, val);
	}

	/**
	 * LPOP
	 * 
	 * @param key
	 * @return
	 */
	public String rpop(String key) {
		return redisTemplate.opsForList().rightPop(key);
	}
}
