package org.nina.api.controller;

import org.apache.commons.lang3.StringUtils;
import org.nina.commons.utils.NinaJsonResult;
import org.nina.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api(value = "注册登录", tags = "用于注册登录的相关接口")
@RestController
@RequestMapping("/passport")
public class PassportController {
	@Autowired
	private UserService userService;

	@ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
	@GetMapping
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
}
