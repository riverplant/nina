package org.nina.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.nina.commons.enums.YesOrNo;
import org.nina.commons.utils.JsonUtils;
import org.nina.commons.utils.NinaJsonResult;
import org.nina.commons.utils.RedisOperator;
import org.nina.domain.Carousel;
import org.nina.dto.CategoryInfo;
import org.nina.dto.vo.CategoryVO;
import org.nina.dto.vo.NewItemsVO;
import org.nina.service.CarouselService;
import org.nina.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "首页", tags = "用于首页的相关接口")
@RestController
@RequestMapping("/index")
public class IndexController {
	private Logger logger = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private CarouselService carouselService;
	@Autowired
	private CategoryService categoryService;
    @Autowired
    private RedisOperator redisOperator;
	
	@ApiOperation(value = "轮播图", notes = "获取首页轮播图列表", httpMethod = "GET")
	@GetMapping("/carousel")
	public NinaJsonResult carousel() {
		List<Carousel>list = new ArrayList<>();
		//查询缓存
		String carouselStr = redisOperator.get("carousel");
		//如果缓存为空
		if(StringUtils.isAllEmpty(carouselStr)){
			list = carouselService.querAll(YesOrNo.YES.trype);
			//将数据库返回内容放入redis缓存
			redisOperator.set("carousel", JsonUtils.objectToJson(list));	
		}else {
			list = JsonUtils.jsonToList(carouselStr, Carousel.class);
		}		
		return NinaJsonResult.ok(list);
	}
	/**
	 * 1.后台运营系统，一旦广告发生更改，可以删除缓存，然后重置
	 * 2.定时重置
	 * 3.每个轮播图都有可能是一个广告，每个广告都有一个过期时间
	 */

	/**
	 * 首页分类展示需求: 
	 * 1.第一次刷新主页查询大分类 
	 * 2.如果鼠标移到大分类,则加载其子类的内容,如果已经存在子分类 则不需要加载
	 */
	@ApiOperation(value = "获取商品分类(一级分类)", notes = "获取商品分类(一级分类)", httpMethod = "GET")
	@GetMapping("/cats")
	public NinaJsonResult queryCats() {
		List<CategoryInfo>list = new ArrayList<>();
		//查询缓存
		String categoryinfos = redisOperator.get("categoryinfos");
		if(StringUtils.isAllEmpty(categoryinfos)){
			list = categoryService.queryAllRootLevel();
			//将数据库返回内容放入redis缓存
			redisOperator.set("categoryinfos", JsonUtils.objectToJson(list));	
		}else {
			list = JsonUtils.jsonToList(categoryinfos, CategoryInfo.class);
		}		
		return NinaJsonResult.ok(list);
	}

	/**
	 * 如果鼠标移到大分类,则加载其子类的内容,如果已经存在子分类 则不需要加载
	 */
	@ApiOperation(value = "首页分类展示", notes = "首页分类展", httpMethod = "GET")
	@GetMapping("/subCats/{id:\\d+}")
	public NinaJsonResult querySubCats(
			@ApiParam(name = "id", value = "一级分类Id", required = true) 
			@PathVariable Long id) {
		if (id == null) {
			return NinaJsonResult.erorMsg("分类不存在");
		}
		List<Object>list = new ArrayList<>();
		//先查询redis缓存
		String categoryinfos = redisOperator.get("subcat:"+id);
		
		if(StringUtils.isAllEmpty(categoryinfos)){
			list = categoryService.querySubCategory(id);
			if(list != null && list.size() >0) {
				//将数据库返回内容放入redis缓存
				redisOperator.set("subcat:"+id, JsonUtils.objectToJson(list));		
			}else {
				//如果数据库查询结果为空，则存入redis一个空结果并且设置过期时间，
				//用来防止缓存穿透
				redisOperator.set("subcat:"+id, JsonUtils.objectToJson(list),5*60);
			}
			
			
		
		}else {
			list = JsonUtils.jsonToList(categoryinfos, Object.class);
		}	
		return NinaJsonResult.ok(list);
	}

	/**
	 * 如果鼠标下滚,则加载其子类的内容,懒加载的模式
	 */
	@ApiOperation(value = "下滚分类展示每个一级分类下的最新6条商品数据", notes = "如果鼠标下滚,则加载其子类的内容,懒加载的模式", httpMethod = "GET")
	@GetMapping("/sixNewItems/{id:\\d+}")
	public NinaJsonResult querysixNewItems(
			@ApiParam(name = "id", value = "一级分类Id", required = true) @PathVariable Long id) {
		if (id == null) {
			return NinaJsonResult.erorMsg("分类不存在");
		}
		List<NewItemsVO> result = categoryService.getSixNewItemsLazy(id);
		return NinaJsonResult.ok(result);
	}

}
