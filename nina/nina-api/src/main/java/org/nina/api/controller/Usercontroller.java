package org.nina.api.controller;

import javax.validation.Valid;

import org.nina.dto.UserInfo;
import org.nina.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/user")
public class Usercontroller {
	@Autowired private UserService userService;
	
	/**
	 * 
	 * @param info
	 * @param result:校验结果
	 * @return
	 */
	@PostMapping
	public UserInfo create(@Valid @RequestBody UserInfo info,BindingResult result) {
		if(result.hasErrors()) {
			result.getAllErrors().stream().forEach(System.out::println);
			throw new RuntimeException(result.toString());
		}	
		return userService.createUser(info);
	}
}
