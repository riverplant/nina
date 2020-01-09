package org.nina.api.controller;

import org.apache.commons.lang3.StringUtils;
import org.nina.commons.utils.NinaJsonResult;
import org.nina.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/passport")
public class PassportController {
	@Autowired
	private UserService userService;

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
