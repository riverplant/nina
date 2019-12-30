package org.nina.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.validation.Valid;

import org.nina.dto.ItemsCondition;
import org.nina.dto.ItemsInfo;
import org.nina.dto.ItemsInfo.ItemsDetailView;
import org.nina.dto.ItemsInfo.ItemsListView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * 
 * @author riverplant
 *
 */
@RestController
@RequestMapping("/items")
public class ItemsController {

	private ConcurrentMap<Long, DeferredResult<ItemsInfo>> map = new ConcurrentHashMap<>() ;
	// @GetMapping()
	// public List<ItemsInfo> query(@RequestParam(value = "name",defaultValue =
	// "炸鸡")String name ,
	// @RequestParam(value = "catagoryId") long catagoryId) {
	// return new ArrayList<ItemsInfo>();
	// }

	@GetMapping
	@JsonView(ItemsListView.class)
	public List<ItemsInfo> query(ItemsCondition condition, @PageableDefault(size = 10) Pageable pageable) {
		System.out.println(pageable.getPageNumber());
		System.out.println(pageable.getPageSize());
		System.out.println(pageable.getSort());
		return new ArrayList<ItemsInfo>();
	}
    /**
     * {id:\\d}:只能有一位
     * @param id
     * @return
     */
	@GetMapping("/{id:\\d+}")
	@JsonView(ItemsDetailView.class)
	public ItemsInfo getInfo(@PathVariable Long id, @CookieValue String token, @RequestHeader String auth) {
		System.out.println("token:"+token);
		System.out.println("auth:"+auth);
		return new ItemsInfo();
	}
	
	/**
     * {id:\\d}:只能有一位
     * @param id
     * @return
     */
	@GetMapping("/{id:\\d+}")
	@JsonView(ItemsDetailView.class)
	public Callable<ItemsInfo> getInfoAsyn(@PathVariable Long id, @CookieValue String token, @RequestHeader String auth) {
		long start = new Date().getTime();
		System.out.println(Thread.currentThread().getName()+ "开始");
		Callable<ItemsInfo> result = () ->{
			System.out.println(Thread.currentThread().getName()+ "开始");
			//模拟调用远程服务需要一秒时间
			Thread.sleep(1000);
			ItemsInfo itemsInfo = new ItemsInfo();
			itemsInfo.setItemName("订购水果");
			System.out.println(Thread.currentThread().getName()+ "线程返回耗时:"+(new Date().getTime()-start));
			return itemsInfo;
		};
		System.out.println(Thread.currentThread().getName()+ "耗时:"+(new Date().getTime()-start));
		return result;
	}
	
	/**
     * {id:\\d}:只能有一位
     * @param id
     * @return
     */
	@GetMapping("/{id:\\d+}")
	@JsonView(ItemsDetailView.class)
	public DeferredResult<ItemsInfo> getDeferredResult(@PathVariable Long id, @CookieValue String token, @RequestHeader String auth) {
		long start = new Date().getTime();
		DeferredResult<ItemsInfo> result = new DeferredResult<>();
		System.out.println(Thread.currentThread().getName()+ "开始");
		map.put(id, result);
		System.out.println(Thread.currentThread().getName()+ "耗时:"+(new Date().getTime()-start));
		return result;
	}
	/**
	 * 
	 * @param itemsInfo
	 */
	@SuppressWarnings("unused")
	private void listenMessage(ItemsInfo itemsInfo) {
		map.get(itemsInfo.getId()).setResult(itemsInfo);
	}
	/**
	 * 
	 * @param info
	 * @param result:校验结果
	 * @return
	 */
	@PostMapping
	public ItemsInfo create(@Valid @RequestBody ItemsInfo info,BindingResult result) {
		if(result.hasErrors()) {
			result.getAllErrors().stream().forEach(System.out::println);
			throw new RuntimeException(result.toString());
		}
		info.setId(1L);
		System.out.println(info.getItemName());
		return info;
	}
	
	@PutMapping("/{id\\d+}")
	public ItemsInfo update(@Valid @RequestBody ItemsInfo info,BindingResult result) {
		if(result.hasErrors()) {
			result.getAllErrors().stream().forEach(System.out::println);
			throw new RuntimeException(result.toString());
		}
		System.out.println(info.getItemName());
		return info;
	}
	
	@DeleteMapping("/{id\\d+}")
	public void delete(@PathVariable Long id) {
		System.out.println(id);
	}
}
