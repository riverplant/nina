package org.nina.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;
/**
 * Redis测试controller
 * @author riverplant
 *
 */
@ApiIgnore
@RestController
@RequestMapping("redis")
public abstract class RedisController {
    @Autowired RedisTemplate<String, Object> redisTemplate;
	
    @GetMapping("/set")
	public void set(String key, String value) {
    	//setnx age 18
    	//redisTemplate.opsForValue()：操作String类型
    	redisTemplate.opsForValue().setIfAbsent(key, value);    	
	}
	
	@GetMapping("/get")
	public Object get(String key) {
		return redisTemplate.opsForValue().get(key);
	}
	/**
	 * mget 批量查询
	 * @param keys
	 * @return
	 */
	@GetMapping("/mget")
	public List<Object> mget(String... keys) {
		List<String> params = Arrays.asList(keys);
		return redisTemplate.opsForValue().multiGet(params);
	}
	
	@DeleteMapping("/delete")
	public Boolean delete(String key) {
		return redisTemplate.delete(key);
	}

}
