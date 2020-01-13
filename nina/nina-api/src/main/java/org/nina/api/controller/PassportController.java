package org.nina.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.nina.commons.utils.CookieUtils;
import org.nina.commons.utils.JsonUtils;
import org.nina.commons.utils.MD5Utils;
import org.nina.commons.utils.NinaJsonResult;
import org.nina.dto.UserInfo;
import org.nina.dto.UserInfo.UserListView;
import org.nina.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api(value = "注册登录", tags = "用于注册登录的相关接口")
@RestController
@RequestMapping("/passport")
public class PassportController {
	@Autowired
	private UserService userService;

	@ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
	@GetMapping(value = "/isexist",produces = {"application/json; charset=UTF-8"})
	public NinaJsonResult usernameIsExist(@RequestParam String username) {
        //1.判断用户名是否为空
		if(StringUtils.isBlank(username)) {
			return NinaJsonResult.erorMsg("用户名不能为空");
		}
		boolean isExist = userService.queryUsernameIsExist(username);
		if(isExist) {
			return NinaJsonResult.erorMsg("用户名已经存在");
		}
		return NinaJsonResult.ok();
	}
	
	
	/**
	 * 
	 * @param info
	 * @param request:用于生成Cookie
	 * @param respnse
	 * @param result:校验结果
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
	@JsonView(UserListView.class)
	@PostMapping(value = "/login",produces = {"application/json; charset=UTF-8"})
	public NinaJsonResult login(@Valid @RequestBody UserInfo info,HttpServletRequest request, HttpServletResponse response,BindingResult result) throws Exception {
		if(result.hasErrors()) {
			result.getAllErrors().stream().forEach(System.out::println);
			return NinaJsonResult.erorMsg(result.toString());
		}
		String username = info.getUsername();
		String password = MD5Utils.getMD5Str(info.getPassword());
		boolean isExist = userService.queryUsernameIsExist(username);
		if(!isExist) {
			return NinaJsonResult.erorMsg("用户名不存在");
		}
		UserInfo resultData = userService.queryUserForLogin(username,password);
		if(resultData == null) {
			return NinaJsonResult.erorMsg("密码与用户名不匹配");
		}
		//设置浏览器端Cookies
		CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(resultData),true);
		return NinaJsonResult.ok(resultData);
	}
	/**
	 * 
	 * @param info
	 * @param request
	 * @param response
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "用户退出", notes = "用户退出登录", httpMethod = "POST")
	@PostMapping(value = "/logout",produces = {"application/json; charset=UTF-8"})
	public NinaJsonResult logOut(@RequestParam Long id ,HttpServletRequest request, HttpServletResponse response) throws Exception {

		//清除浏览器端用户相关Cookies
		CookieUtils.deleteCookie(request, response, "user");
		//TODO 用户退出登录.需要清空购物车
		
		//分布会话中需要清除用户数据
		
		return NinaJsonResult.ok();
	}
}
