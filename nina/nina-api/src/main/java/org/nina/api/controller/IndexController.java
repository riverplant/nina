package org.nina.api.controller;

import java.util.List;

import org.nina.commons.enums.YesOrNo;
import org.nina.commons.utils.NinaJsonResult;
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

	@ApiOperation(value = "轮播图", notes = "获取首页轮播图列表", httpMethod = "GET")
	@GetMapping("/carousel")
	public NinaJsonResult carousel() {
		List<Carousel> result = carouselService.querAll(YesOrNo.YES.trype);
		return NinaJsonResult.ok(result);
	}

	/**
	 * 首页分类展示需求: 
	 * 1.第一次刷新主页查询大分类 
	 * 2.如果鼠标移到大分类,则加载其子类的内容,如果已经存在子分类 则不需要加载
	 */
	@ApiOperation(value = "获取商品分类(一级分类)", notes = "获取商品分类(一级分类)", httpMethod = "GET")
	@GetMapping("/cats")
	public NinaJsonResult queryCats() {
		List<CategoryInfo> result = categoryService.queryAllRootLevel();
		return NinaJsonResult.ok(result);
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
		List<Object> result = categoryService.querySubCategory(id);
		return NinaJsonResult.ok(result);
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
