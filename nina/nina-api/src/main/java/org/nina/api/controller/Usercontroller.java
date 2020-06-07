package org.nina.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.nina.commons.utils.CookieUtils;
import org.nina.commons.utils.JsonUtils;
import org.nina.commons.utils.NinaJsonResult;
import org.nina.dto.AddressInfo;
import org.nina.dto.UserInfo;
import org.nina.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;
@RestController
@ApiIgnore//在API文档中隐藏
@RequestMapping("/user")
public class Usercontroller {
	@Autowired private UserService userService;	
	/**
	 * 
	 * @param info
	 * @param result:校验结果
	 * @return
	 */
	@ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
	@PostMapping
	public NinaJsonResult register(@Valid @RequestBody UserInfo info,HttpServletRequest request, HttpServletResponse response,BindingResult result) {
		if(result.hasErrors()) {
			result.getAllErrors().stream().forEach(System.out::println);
			//throw new RuntimeException(result.toString());
			return NinaJsonResult.erorMsg(result.toString());
		}
		boolean isExist = userService.queryUsernameIsExist(info.getUsername());
		String password = info.getPassword();
		String confirmedpassword = info.getConfirmPassword();
		if(!password.equals(confirmedpassword)) {
			return NinaJsonResult.erorMsg("两次密码输入不一致");
		}
		if(isExist) {
			return NinaJsonResult.erorMsg("用户名已经存在");
		}
		UserInfo resultData = userService.createUser(info);
		//设置浏览器端Cookies
	    CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(resultData),true);
	    
	    //TODO:生成用户Token,存入redis会话
	    //TODO:同步购物车数据
		return NinaJsonResult.ok(resultData);
	}
	
	/**
	 * 用户在确认订单页面，可以针对收货地址做如下操作
	 * 1.查询用户的所有收获地址列表
	 * 2.新增收货地址
	 * 3.删除收货地址
	 * 4.修改收货地址
	 * 5.设置默认地址
	 * @param info
	 * @param result:校验结果
	 * @return
	 */
	@ApiOperation(value = "查询用户所有地址", notes = "查询用户所有地址", httpMethod = "GET")
	@GetMapping("/address/{id\\d+}")
	public NinaJsonResult getUserAddress(@ApiParam(name = "id", value = "用户ID", required = true) @PathVariable Long id,HttpServletRequest request, HttpServletResponse response) {
		String userJson = CookieUtils.getCookieValue(request, "user");
		if(!StringUtils.isBlank(userJson)) {
			UserInfo userInfo =	JsonUtils.jsonToPojo(userJson, UserInfo.class);
			return NinaJsonResult.ok(userInfo.getAddresses());
		} else {
			return NinaJsonResult.ok(userService.getUserAdrress(id));
		}
	}
	
	/**
	 * 用户在确认订单页面，可以针对收货地址做如下操作
	 * 1.查询用户的所有收获地址列表
	 * 2.新增收货地址
	 * 3.删除收货地址
	 * 4.修改收货地址
	 * 5.设置默认地址
	 * @param info
	 * @param result:校验结果
	 * @return
	 */
	@ApiOperation(value = "新增用户地址", notes = "新增用户地址", httpMethod = "POST")
	@PostMapping("/address{id\\d+}")
	public NinaJsonResult addNewAddress(
			@Valid @RequestBody AddressInfo info,
			@ApiParam(name = "id", value = "用户ID", required = true) @PathVariable Long id,
			HttpServletRequest request, 
			HttpServletResponse response,
			BindingResult result) {
		if(result.hasErrors()) {
			return NinaJsonResult.erorMsg(result.toString());
		}
		userService.addNewAddress(info, id);
		return NinaJsonResult.ok();
	}
	
	/**
	 * 用户在确认订单页面，可以针对收货地址做如下操作
	 * 1.查询用户的所有收获地址列表
	 * 2.新增收货地址
	 * 3.删除收货地址
	 * 4.修改收货地址
	 * 5.设置默认地址
	 * @param info
	 * @param result:校验结果
	 * @return
	 */
	@ApiOperation(value = "修改用户地址", notes = "修改用户地址", httpMethod = "PUT")
	@PutMapping("/address{id\\d+}")
	public NinaJsonResult updateAddress(
			@Valid @RequestBody AddressInfo info,
			@ApiParam(name = "id", value = "用户ID", required = true) @PathVariable Long id,
			HttpServletRequest request, 
			HttpServletResponse response,
			BindingResult result) {
		if(result.hasErrors()) {
			return NinaJsonResult.erorMsg(result.toString());
		}
		userService.updateAddress(info, id);
		return NinaJsonResult.ok();
	}
	
}
