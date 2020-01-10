package org.nina.api.controller;

import javax.validation.Valid;

import org.nina.commons.utils.NinaJsonResult;
import org.nina.dto.UserInfo;
import org.nina.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
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
	@ApiOperation(value = "创建用户", notes = "创建用户", httpMethod = "POST")
	@PostMapping
	public NinaJsonResult create(@Valid @RequestBody UserInfo info,BindingResult result) {
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
		return NinaJsonResult.ok(resultData);
	}
	
	
}
