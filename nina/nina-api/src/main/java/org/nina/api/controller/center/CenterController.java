package org.nina.api.controller.center;

import org.nina.commons.utils.NinaJsonResult;
import org.nina.dto.UserInfo;
import org.nina.service.center.CenterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 用户中心接口
 * 
 * @author riverplant
 *
 */
@RestController
@Api(value = "center-用户中心", tags = "用户中心展示的相关接口")
@RequestMapping("center")
public class CenterController {
    @Autowired private CenterUserService centerUserService;
	
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息",httpMethod = "GET")
	@GetMapping("userInfo/{userId:\\\\d+}")
	public NinaJsonResult userInfo(
			@ApiParam(name = "userId",value = "用户ID", required = true)
			@PathVariable Long userId) {
    	UserInfo user = centerUserService.queryUserInfo(userId);
		return NinaJsonResult.ok(user);
	}
    
    
}
