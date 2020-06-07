package org.nina.api.controller.center;

import org.nina.commons.utils.NinaJsonResult;
import org.nina.dto.vo.center.MyOrdersVO;
import org.nina.service.center.MyOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户中心我的订单接口
 * 
 * @author riverplant
 *
 */
@RestController
@Api(value = "center-用户中心我的订单", tags = "用户中心我的订单展示的相关接口")
@RequestMapping("myorders")
public class MyOrdersController {
	@Autowired
	private MyOrderService myOrderService;

	@ApiOperation(value = "获取用户信息", notes = "获取用户信息", httpMethod = "GET")
	@PostMapping("/query")
	public NinaJsonResult query(@RequestParam Long userId, @RequestParam Integer orderStatus,
			@PageableDefault(size = 10) Pageable pageable) {
		if (StringUtils.isEmpty(userId)) {
			return NinaJsonResult.erorMsg(null);
		}
		Page<MyOrdersVO> myOrdersVO = myOrderService.queryMyOrders(userId, orderStatus, pageable);
		return NinaJsonResult.ok(myOrdersVO);
	}

}
