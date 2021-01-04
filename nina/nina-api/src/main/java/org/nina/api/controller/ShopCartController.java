/**
 * 
 */
package org.nina.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.nina.commons.utils.JsonUtils;
import org.nina.commons.utils.NinaJsonResult;
import org.nina.commons.utils.RedisOperator;
import org.nina.dto.vo.ShopcartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author riverplant 当用户未登录：使用cookie 当用户登录： 使用Redis
 */
@Api(value = "购物车接口controller", tags = "购物车相关的接口API")
@RestController
@RequestMapping("/shopCart")
public class ShopCartController extends BaseController{
	 @Autowired
	    private RedisOperator redisOperator;
	/**
	 * 用户登录的情况下，添加商品到购物车调用该接口
	 * @param request
	 * @param userId
	 * @param shopcart 购物车
	 * @return
	 */
	@ApiOperation(value = "用户登录后添加商品到购物车,同步购物车到后端", notes = "用户登录后添加商品到购物车,同步购物车到后端", httpMethod = "POST")
	@PostMapping("/add")
	public NinaJsonResult add(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Long userId,
			@RequestBody ShopcartVO shopcart 
			) {
	   if(StringUtils.isBlank(String.valueOf(userId))) {
		   return NinaJsonResult.erorMsg("");
	   }
	   //Redis 前端用户在登录的情况下，添加商品到购物车，会在后端同步购物车到缓存
	   //需要判断购物车中包含已经存在的商品，如果存在则累加购买数量
	   //通过":"进行存储，可以在redis中自动实现数据的分类，例如SHOPCART:1,SHOPCART:2都会归类为SHOPCART
	   String shopcartJson = redisOperator.get(SHOPCART+":"+userId);
	   List<ShopcartVO>shopcartList = null;
	   if(StringUtils.isNotBlank(shopcartJson)) {
		   //redis中已经有购物车
		   shopcartList = JsonUtils.jsonToList(shopcartJson, ShopcartVO.class);
		   boolean isHave = false;
		   for(ShopcartVO shopcartVO : shopcartList ) {
			   Long specId = shopcartVO.getSpecId();
			   //购物车中包含已经存在的商品，如果存在则累加购买数量
			   if(specId.longValue() == shopcart.getSpecId().longValue()) {
				   shopcartVO.setBuyCounts(shopcartVO.getBuyCounts() + shopcart.getBuyCounts());  
				   isHave = true;
			   }
		   } 
		   //当redis中的购物车列表不包括新添加的货物
		   if(!isHave) {
			   shopcartList.add(shopcart);
		   }
	   } else {
		   //redis中没有购物车
		   shopcartList = new ArrayList<>();
		   shopcartList.add(shopcart);
	   }
	   //覆盖现有的redis中的购物车
	   redisOperator.set(SHOPCART+":"+userId,JsonUtils.objectToJson(shopcartList));	   
	   return  NinaJsonResult.ok() ;
	}
	
	/**
	 * 用户登录的情况下，删除商品到购物车调用该接口
	 * @param request
	 * @param response
	 * @param userId
	 * @param itemSpecId
	 * @return
	 */
	@ApiOperation(value = "用户登录后删除购物车商品,同步购物车到后端", notes = "用户登录后删除购物车商品，同步购物车到后端", httpMethod = "POST")
	@PostMapping("/del")
	public NinaJsonResult del(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Long userId,
			@RequestParam Long itemSpecId
			) {
	   if(StringUtils.isBlank(String.valueOf(userId))) {
		   return NinaJsonResult.erorMsg("userId不能为空");
	   }
	   if(StringUtils.isBlank(String.valueOf(itemSpecId))) {
		   return NinaJsonResult.erorMsg("itemSpecId不能为空");
	   }
	   //Redis 前端用户在登录的情况下，删除购物车商品，会在redis同步购物车到缓存
	   String shopcartJson = redisOperator.get(SHOPCART+":"+userId);
	   List<ShopcartVO>shopcartList = null;
	   if(StringUtils.isNotBlank(shopcartJson)) {
		   //redis中已经有购物车
		   shopcartList = JsonUtils.jsonToList(shopcartJson, ShopcartVO.class);
		   for(ShopcartVO shopcartVO : shopcartList ) {
			   Long specId = shopcartVO.getSpecId();
			   //购物车中包含已经存在的商品，如果存在则累加购买数量
			   if(specId.longValue() == itemSpecId.longValue()) {
				   shopcartList.remove(shopcartVO);
				   break;
			   }
		   } 
	   }
	   redisOperator.set(SHOPCART+":"+userId,JsonUtils.objectToJson(shopcartList));	
	   return  NinaJsonResult.ok() ;
	}
}
