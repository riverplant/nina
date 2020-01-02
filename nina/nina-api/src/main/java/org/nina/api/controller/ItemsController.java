package org.nina.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.nina.dto.ItemsCondition;
import org.nina.dto.ItemsInfo;
import org.nina.dto.ItemsInfo.ItemsDetailView;
import org.nina.dto.ItemsInfo.ItemsListView;
import org.nina.service.ItemsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.fasterxml.jackson.annotation.JsonView;

/**
 * 
 * @author riverplant
 *
 */
@RestController
@RequestMapping("/items")
public class ItemsController {
   private Logger logger = LoggerFactory.getLogger(ItemsController.class);
	@Autowired private ItemsService itemsService;
	// @GetMapping()
	// public List<ItemsInfo> query(@RequestParam(value = "name",defaultValue =
	// "炸鸡")String name ,
	// @RequestParam(value = "catagoryId") long catagoryId) {
	// return new ArrayList<ItemsInfo>();
	// }

	@GetMapping
	@JsonView(ItemsListView.class)
	public Page<ItemsInfo> query(ItemsCondition condition, @PageableDefault(size = 10) Pageable pageable) {
		logger.info("这是日志!!!!");
		System.out.println(pageable.getPageNumber());
		System.out.println(pageable.getPageSize());
		System.out.println(pageable.getSort());
		return itemsService.query(condition, pageable);
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
		//System.out.println(info.getItemName());
		return itemsService.create(info);
	}
	
	@PutMapping("/{id\\d+}")
	public ItemsInfo update(@Valid @RequestBody ItemsInfo info,BindingResult result) {
		if(result.hasErrors()) {
			result.getAllErrors().stream().forEach(System.out::println);
			throw new RuntimeException(result.toString());
		}
		//System.out.println(info.getItemName());
		return itemsService.update(info);
	}
	
	@DeleteMapping("/{id\\d+}")
	public void delete(@PathVariable Long id) {
		System.out.println(id);
		itemsService.delete(id);
	}
}
