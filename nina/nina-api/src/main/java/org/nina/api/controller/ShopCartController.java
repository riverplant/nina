/**
 * 
 */
package org.nina.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.nina.commons.utils.NinaJsonResult;
import org.nina.dto.vo.ShopcartVO;
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
public class ShopCartController {
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
			@RequestParam String userId,
			@RequestBody ShopcartVO shopcart 
			) {
	   if(StringUtils.isBlank(userId)) {
		   return NinaJsonResult.erorMsg("");
	   }
	   //TODO Redis 前端用户在登录的情况下，添加商品到购物车，会在后端同步购物车到缓存
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
			@RequestParam String userId,
			@RequestParam String itemSpecId
			) {
	   if(StringUtils.isBlank(userId)) {
		   return NinaJsonResult.erorMsg("userId不能为空");
	   }
	   if(StringUtils.isBlank(itemSpecId)) {
		   return NinaJsonResult.erorMsg("itemSpecId不能为空");
	   }
	   String[] specIds = itemSpecId.split(",");
	   //TODO Redis 前端用户在登录的情况下，删除购物车商品，会在后端同步购物车到缓存
		return  NinaJsonResult.ok() ;
	}
}
