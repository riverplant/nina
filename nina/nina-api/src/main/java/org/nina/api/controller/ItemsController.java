package org.nina.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.nina.commons.utils.NinaJsonResult;
import org.nina.dto.ItemsCondition;
import org.nina.dto.ItemsInfo;
import org.nina.dto.ItemsInfo.ItemsDetailView;
import org.nina.dto.ItemsInfo.ItemsListView;
import org.nina.dto.ItemsParamInfo;
import org.nina.dto.ItemsSpecInfo;
import org.nina.dto.Items_imgInfo;
import org.nina.dto.vo.CommentLevelCountsVO;
import org.nina.dto.vo.ItemInfosVO;
import org.nina.dto.vo.ShopcartVO;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author riverplant
 *
 */
@Api(value = "商品接口", tags = "商品信息展示的相关接口")
@RestController
@RequestMapping("/items")
public class ItemsController {
	private Logger logger = LoggerFactory.getLogger(ItemsController.class);
	@Autowired
	private ItemsService itemsService;

	// @GetMapping()
	// public List<ItemsInfo> query(@RequestParam(value = "name",defaultValue =
	// "炸鸡")String name ,
	// @RequestParam(value = "catagoryId") long catagoryId) {
	// return new ArrayList<ItemsInfo>();
	// }
	@ApiOperation(value = "查询商品接口", notes = "根据条件查询商品接口", httpMethod = "GET")
	@GetMapping
	@JsonView(ItemsListView.class)
	public Page<ItemsInfo> query(ItemsCondition condition, @PageableDefault(size = 10) Pageable pageable) {
		logger.info("这是日志!!!!");
		System.out.println(pageable.getPageNumber());
		System.out.println(pageable.getPageSize());
		System.out.println(pageable.getSort());
		/**
		 * 
		 */
		return itemsService.query(condition, pageable);
	}

	/**
	 * {id:\\d}:只能有一位
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "查询商品详情", notes = "根据商品ID查询商品详情", httpMethod = "GET")
	// @GetMapping("/{id:\\d+}")
	@JsonView(ItemsDetailView.class)
	public ItemsInfo getInfo(@ApiParam(name = "id", value = "商品ID", required = true) @PathVariable Long id,
			@CookieValue String token, @RequestHeader String auth) {
		System.out.println("token:" + token);
		System.out.println("auth:" + auth);
		return new ItemsInfo();
	}

	/**
	 * 
	 * @param info
	 * @param result:校验结果
	 * @return
	 */
	@ApiOperation(value = "创建商品", notes = "创建商品", httpMethod = "POST")
	@PostMapping
	public ItemsInfo create(@Valid @RequestBody ItemsInfo info, BindingResult result) {
		if (result.hasErrors()) {
			result.getAllErrors().stream().forEach(System.out::println);
			throw new RuntimeException(result.toString());
		}
		// System.out.println(info.getItemName());
		return itemsService.create(info);
	}

	/**
	 * 
	 * @param info
	 * @param result
	 * @return
	 */
	@ApiOperation(value = "更新商品", notes = "更新商品", httpMethod = "PUT")
	@PutMapping("/{id\\d+}")
	public ItemsInfo update(@ApiParam(name = "id", value = "商品ID", required = true) @PathVariable Long id,
			@Valid @RequestBody ItemsInfo info, BindingResult result) {
		if (result.hasErrors()) {
			result.getAllErrors().stream().forEach(System.out::println);
			throw new RuntimeException(result.toString());
		}
		if (info.getId() != id) {
			throw new RuntimeException("id is not match");
		}
		return itemsService.update(info);
	}

	/**
	 * 
	 * @param id
	 */
	@ApiOperation(value = "删除商品", notes = "删除商品", httpMethod = "DELETE")
	@DeleteMapping("/{id\\d+}")
	public void delete(@ApiParam(name = "id", value = "商品ID", required = true) @PathVariable Long id) {
		System.out.println(id);
		itemsService.delete(id);
	}

	/**
	 * {id:\\d}:只能有一位
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "查询商品详情2", notes = "根据商品ID查询商品详情2", httpMethod = "GET")
	@GetMapping("/{id:\\d+}")
	@JsonView(ItemsDetailView.class)
	public NinaJsonResult getInfo2(@ApiParam(name = "id", value = "商品ID", required = true) @PathVariable Long id) {
		if (id == null) {
			return NinaJsonResult.erorMsg("id is null");
		}
		ItemsInfo itemsInfo = itemsService.queryById(id);
		List<Items_imgInfo> itemsImgInfosList = itemsService.queryItemImgList(id);
		ItemsParamInfo itemsParamInfo = itemsService.queryItemParam(id);
		List<ItemsSpecInfo> itemsSpecInfosList = itemsService.queryItemSpecList(id);
		ItemInfosVO itemInfosVO = new ItemInfosVO();
		itemInfosVO.setItemsImgInfosList(itemsImgInfosList);
		itemInfosVO.setItemsInfo(itemsInfo);
		itemInfosVO.setItemsParamInfo(itemsParamInfo);
		itemInfosVO.setItemsSpecInfosList(itemsSpecInfosList);
		return NinaJsonResult.ok(itemInfosVO);
	}
	
	/**
	 * {id:\\d}:只能有一位
	 * 用于用户长时间未登录网站，刷新购物车数据，主要是更新商品价格
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据商品规格ids查找最新的商品数据", notes = "根据商品规格ids查找最新的商品数据", httpMethod = "GET")
	@GetMapping("/refresh")
	@JsonView(ItemsDetailView.class)
	public NinaJsonResult refresh(@ApiParam(name = "itemSpecIds", value = "商品规格id拼接字符串", required = true,example="1001,1003,...") @PathVariable String itemSpecIds) {
		if (StringUtils.isBlank(itemSpecIds)) {
			//当前端传递一个空规格ID，返回一个空的购物车数据
			return NinaJsonResult.ok();
		}
		List<ShopcartVO> shopcartVo = itemsService.queryItemsBySpecIds(itemSpecIds);
		return NinaJsonResult.ok(shopcartVo);
	}

	/**
	 * {id:\\d}:只能有一位
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据商品ID查询商品所有评价等级和数量", notes = "根据商品ID查询商品所有评价等级和数量", httpMethod = "GET")
	@GetMapping("/{id:\\d+}/commentLevelAndCount")
	public NinaJsonResult queryCommentLevelsAndCounts(
			@ApiParam(name = "id", value = "商品ID", required = true) @PathVariable Long id) {
		if (id == null) {
			return NinaJsonResult.erorMsg("id is null");
		}
		CommentLevelCountsVO commentLevelCountsVO = new CommentLevelCountsVO();
		commentLevelCountsVO = itemsService.queryCommentLevelsAndCounts(id);
		return NinaJsonResult.ok(commentLevelCountsVO);
	}
}
