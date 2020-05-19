package org.nina.service;
import java.util.Date;
import java.util.Objects;

import org.nina.commons.enums.OrderStatusEnum;
import org.nina.commons.enums.YesOrNo;
import org.nina.domain.Address;
import org.nina.domain.Items;
import org.nina.domain.ItemsSpec;
import org.nina.domain.OrderDetail;
import org.nina.domain.OrderStatus;
import org.nina.domain.Orders;
import org.nina.domain.User;
import org.nina.dto.vo.OrderVO;
import org.nina.dto.vo.PayOrdersVO;
import org.nina.dto.vo.SubmitOrderVO;
import org.nina.repository.OrderDetailRepository;
import org.nina.repository.OrderRepository;
import org.nina.repository.OrderStatusRepository;
import org.nina.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/**
 * 通过定义transactionManager.commit来保持上下文的一致性
 * @author riverplant
 *
 */
@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

	@Autowired private OrderRepository orderRepository;
	@Autowired private UserRepository userRepository;
	@Autowired private OrderDetailRepository orderDetailRepository;
	@Autowired private ItemsServiceImpl itemsServiceImpl;
	@Autowired private OrderStatusRepository orderStatusRepository;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public OrderVO createOrder(SubmitOrderVO submitOrderVO) {
		Long userId = submitOrderVO.getUserId();
		int addressSord = submitOrderVO.getChoosedAddressSord();
		String itemSpecIds = submitOrderVO.getItemSpecIds();
		Integer payMethod = submitOrderVO.getChoosedPayMethod();
		String leftMsg = submitOrderVO.getOrderRemarker();
		//邮费
		Integer postAmount = 0;
		
		//1.新订单数据保存
		User user = userRepository.findById(userId).orElse(null);
		if(user == null) {
			throw new RuntimeException("user is null");
		}
		Orders order = new Orders();
		order.setUserId(userId);
	    Address address = user.getAddresseByOrder(addressSord);
	    if(address == null) {
	    	throw new RuntimeException("address is null");
	    }
	    order.setReceiverAddress(
	    		address.getProvince()+""
	            +address.getCity()+""
	            +address.getDistrict()+""
	            +address.getAddress());
    	order.setReceiverName(user.getRealname());
    	order.setReceiverMobile(user.getMobile());
    	order.setPostAmount(postAmount);//邮费暂定为0,实际中从数据库查询
    	order.setPayMethod(payMethod);
    	order.setLeftMsg(leftMsg);
    	order.setIsComment(YesOrNo.NO.trype);
    	order.setIsDelete(YesOrNo.NO.trype);
    	
		//2.循环根据itemSpecIds保存订单商品信息表
    	String[] itemSpec = itemSpecIds.split(",");
    	Integer totalAmount = 0; //商品原价累计
    	Integer reakPayAmount = 0; //优惠后实际价格累计
    	for(String i : itemSpec) {
    		//TODO 整合redis后，商品购买的数量重新从redis的购物车中获取
    		int buyCounts = 1;
    		
    		//2.1 根据规格id查询规格具体信息，主要是价格
    		ItemsSpec itemsSpec = itemsServiceImpl.queryItemSpecById(Long.valueOf(i));
    		totalAmount += itemsSpec.getPriceNormal() * buyCounts ;
    		reakPayAmount += itemsSpec.getPriceDiscount() * buyCounts;
    	    
    		//2.2 根据商品id获得商品信息以及商品图片
    		Items item = itemsSpec.getItems();
    		//获得商品图片主图的url
    		String imgUrl = item.getItemsImg().stream()
    		                  .filter(img -> img.isMain())
    		                  .filter(Objects::nonNull)
    		                  .map(img -> img.getUrl())
    		                  .findFirst().get();
    		//2.3循环保存子订单数据到数据库
        	OrderDetail subOrderItem = new OrderDetail();
        	subOrderItem.setItemId(item.getId());
        	subOrderItem.setItemName(item.getItemName());
        	subOrderItem.setItemImg(imgUrl);
        	subOrderItem.setBuyCounts(buyCounts);
        	subOrderItem.setItemSpecId(itemsSpec.getId());
        	subOrderItem.setItemSpecName(itemsSpec.getName());
        	subOrderItem.setPrice(itemsSpec.getPriceDiscount());
        	orderDetailRepository.save(subOrderItem);
        	
        	//2.4在用户提交订单之后，规格表中需要扣除库存
        	itemsServiceImpl.decreaseItemSpecStocke(itemsSpec.getId(), buyCounts);
    	}
    	order.setTotalAmount(totalAmount);
    	order.setRealPayAmount(reakPayAmount);
    	orderRepository.save(order);//通过循环itemSpecIds获得价格后
		
    	//3.保存订单状态表
    	OrderStatus waitPayorderStatus = new OrderStatus();
    	waitPayorderStatus.setOrderId(order.getId());
    	waitPayorderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.trype);
    	orderStatusRepository.save(waitPayorderStatus);
    	//4.构建商户订单，用于传给支付中心
    	PayOrdersVO payOrdersVO = new PayOrdersVO();
    	payOrdersVO.setMerchantOrderId(String.valueOf(order.getId()));
    	payOrdersVO.setMerchantUserId(String.valueOf(user.getId()));
    	payOrdersVO.setAmount(reakPayAmount + postAmount);
    	payOrdersVO.setPayMethod(payMethod);
    	
    	//5.构建自定订单VO
    	OrderVO orderVO = new OrderVO();
    	orderVO.setOrderId(String.valueOf(order.getId()));
    	orderVO.setPayOrdersVO(payOrdersVO);
    	return orderVO;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void updateOrderStatus(Long orderId, Integer orderStatus) {
		OrderStatus paidStatus = orderStatusRepository.getByOrderId(orderId);
		paidStatus.setOrderStatus(orderStatus);
		paidStatus.setPayTime(new Date());
		orderStatusRepository.save(paidStatus);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public OrderStatus queryOrderStatusInfo(Long orderId) {
		return orderStatusRepository.getByOrderId(orderId);
	}
	
}