package org.nina.api.controller.center;

import java.util.List;

import org.nina.api.controller.BaseController;
import org.nina.commons.enums.YesOrNo;
import org.nina.commons.utils.NinaJsonResult;
import org.nina.domain.OrderDetail;
import org.nina.domain.Orders;
import org.nina.dto.vo.center.OrderItemsCommentVO;
import org.nina.service.center.MyCommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户中心我的评价接口
 * 
 * @author riverplant
 *
 */
@RestController
@Api(value = "用户中心评价模块", tags = "用户中心评价模块的相关接口")
@RequestMapping("mycomments")
public class MyCommentsController extends BaseController{
	@Autowired
	private MyCommentsService myCommentsService;

	@ApiOperation(value = "获取订单分页列表信息", notes = "获取订单分页列表信息", httpMethod = "POST")
	@PostMapping("/query")
	public NinaJsonResult query(
			@RequestParam Long orderId, 
			@RequestParam Long userId,
			@PageableDefault(size = 10) Pageable pageable) {
		if (StringUtils.isEmpty(orderId) || StringUtils.isEmpty(userId)) {
			return NinaJsonResult.erorMsg(null);
		}
		Orders order = checkUserOrder(userId, orderId);
		if(order == null) {
			return NinaJsonResult.erorMsg("该用户无权评价该订单");
		};
		if(order.getIsComment() == YesOrNo.YES.trype) {
			return NinaJsonResult.erorMsg("该订单已经评价");
		}; 
		Page<OrderDetail> orderDetails = myCommentsService.queryPendingComment(orderId, pageable);
		return NinaJsonResult.ok(orderDetails);
	}
	
	@ApiOperation(value = "保存评论列表", notes = "保存评论列表", httpMethod = "POST")
	@PostMapping("/saveList")
	public NinaJsonResult saveList(
			@RequestParam Long orderId, 
			@RequestParam Long userId,
			@RequestBody List<OrderItemsCommentVO> commentList) {
		if (StringUtils.isEmpty(orderId) || StringUtils.isEmpty(userId)) {
			return NinaJsonResult.erorMsg(null);
		}
		//判断评论list是否为空，如果为空不需要保存
		if(commentList == null || commentList.isEmpty() || commentList.size() == 0) {
			return NinaJsonResult.erorMsg(null);
		}
		Orders order = checkUserOrder(userId, orderId);
		if(order == null) {
			return NinaJsonResult.erorMsg("该用户无权评价该订单");
		};
		
		if(order.getIsComment() == YesOrNo.YES.trype) {
			return NinaJsonResult.erorMsg("该订单已经评价");
		}; 
		myCommentsService.saveComments(userId, orderId, commentList);
		return NinaJsonResult.ok();
	}

}
