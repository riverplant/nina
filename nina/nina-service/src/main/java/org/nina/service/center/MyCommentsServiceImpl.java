package org.nina.service.center;
import java.util.Date;
import java.util.List;

import org.nina.commons.enums.CommentLevel;
import org.nina.commons.enums.YesOrNo;
import org.nina.domain.Items;
import org.nina.domain.ItemsComments;
import org.nina.domain.OrderDetail;
import org.nina.domain.OrderStatus;
import org.nina.domain.Orders;
import org.nina.domain.User;
import org.nina.dto.vo.center.OrderItemsCommentVO;
import org.nina.repository.ItemsCommentRepository;
import org.nina.repository.ItemsRepository;
import org.nina.repository.OrderDetailRepository;
import org.nina.repository.OrderRepository;
import org.nina.repository.OrderStatusRepository;
import org.nina.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/**
 * 通过定义transactionManager.commit来保持上下文的一致性
 * @author riverplant
 * @param <V>
 *
 */
@Service
@Transactional(readOnly = true)
public class MyCommentsServiceImpl<V> implements MyCommentsService {

	@Autowired private OrderDetailRepository orderDetailRepository;
	@Autowired private OrderRepository orderRepository;
	@Autowired private ItemsCommentRepository itemsCommentRepository;
	@Autowired private ItemsRepository itemsRepository;
	@Autowired private UserRepository userRepository;
	@Autowired private OrderStatusRepository orderStatusRepository;
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Page<OrderDetail> queryPendingComment(Long orderId, Pageable pageable) {
		OrderDetail de = new OrderDetail();
		de.setOrderId(orderId);
		Example<OrderDetail> example = Example.of(de);
		return orderDetailRepository.findAll(example, pageable);
	}
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void saveComments(Long userId, Long orderId, List<OrderItemsCommentVO> commentList) {
		User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("找不到该用户"));
		Orders order = orderRepository.findById(orderId).orElseThrow(()-> new RuntimeException("找不到该订单"));
		Date updateTime = new Date(); 
		//1.保存评价items_comments
		for(OrderItemsCommentVO orderItemsCommentVO : commentList) {
			ItemsComments itemsComments = new ItemsComments();
			itemsComments.setUser(user);
			itemsComments.setCommentLevel(CommentLevel.stateOf(orderItemsCommentVO.getCommentLevel()));
			itemsComments.setItemSpecId(orderItemsCommentVO.getItemSPecId());
			Items items = itemsRepository.getOne(orderItemsCommentVO.getItemId());
			itemsComments.setItems(items);
			itemsComments.setItemName(orderItemsCommentVO.getItemName());
			itemsComments.setContent(orderItemsCommentVO.getContent());
			itemsComments.setUpdatedTime(updateTime);
			itemsCommentRepository.save(itemsComments);
		}
		//2.修改订单表为已评价:orders
		order.setIsComment(YesOrNo.YES.trype);
		order.setUpdatedTime(updateTime);
		orderRepository.save(order);
		//3.订单状态表留言时间:order_status
		OrderStatus orderStatus = orderStatusRepository.getByOrderId(orderId);
		if(orderStatus == null) throw new RuntimeException("找不到orderStatus");
		orderStatus.setCommentTime(updateTime);
		orderStatusRepository.save(orderStatus);
	}

}