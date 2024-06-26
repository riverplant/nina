package org.nina.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.nina.commons.enums.OrderStatusEnum;
import org.nina.commons.enums.YesOrNo;
import org.nina.commons.utils.DateUtil;
import org.nina.domain.Address;
import org.nina.domain.Items;
import org.nina.domain.ItemsSpec;
import org.nina.domain.OrderDetail;
import org.nina.domain.OrderStatus;
import org.nina.domain.Orders;
import org.nina.domain.User;
import org.nina.dto.vo.OrderVO;
import org.nina.dto.vo.PayOrdersVO;
import org.nina.dto.vo.ShopcartVO;
import org.nina.dto.vo.SubmitOrderVO;
import org.nina.repository.OrderDetailRepository;
import org.nina.repository.OrderRepository;
import org.nina.repository.OrderStatusRepository;
import org.nina.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
public class OrderServiceImpl<V> implements OrderService {

	@Autowired private OrderRepository orderRepository;
	@Autowired private UserRepository userRepository;
	@Autowired private OrderDetailRepository orderDetailRepository;
	@Autowired private ItemsServiceImpl itemsServiceImpl;
	@Autowired private OrderStatusRepository orderStatusRepository;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public OrderVO createOrder(SubmitOrderVO submitOrderVO, List<ShopcartVO>  shopcartList) {
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
    	//生成订单后即将从购物车里移除的商品
    	List<ShopcartVO> toBeRemoveShopCartList = new ArrayList<>();
    	for(String i : itemSpec) {
    		//TODO 整合redis后，商品购买的数量重新从redis的购物车中获取
    		ShopcartVO shopItem = getBuyCount(shopcartList, i);
    		if(shopItem == null) {
    			throw new RuntimeException("itemSpecId错误");
    		}
    		int buyCounts = shopItem.getBuyCounts();
    		toBeRemoveShopCartList.add(shopItem);
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
    	//4.构建用户自己开发的支付中心的商户订单，用于传给支付中心
    	PayOrdersVO payOrdersVO = new PayOrdersVO();
    	payOrdersVO.setMerchantOrderId(String.valueOf(order.getId()));
    	payOrdersVO.setMerchantUserId(String.valueOf(user.getId()));
    	payOrdersVO.setAmount(reakPayAmount + postAmount);
    	payOrdersVO.setPayMethod(payMethod);
    	
    	//5.构建系统自定订单VO，包含支付中心的商户订单，通过返回给controller
    	OrderVO orderVO = new OrderVO();
    	orderVO.setOrderId(String.valueOf(order.getId()));
    	orderVO.setPayOrdersVO(payOrdersVO);
    	//将要删除的商品传给controller,由controller进行删除
    	orderVO.setToBeRemoveShopCartItems(toBeRemoveShopCartList);
    	return orderVO;
	}
    /**
     * 从redis的购物车获得商品
     * @param shopcartList
     * @param id
     * @return
     */
	private ShopcartVO getBuyCount(List<ShopcartVO> shopcartList, String id) {
		ShopcartVO result = shopcartList.stream()
		.filter(shopcart->StringUtils.equals(String.valueOf(shopcart.getSpecId()), id))
		.findFirst().orElse(null);
		return result;
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
	/**
	 * 关闭订单
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void closeOrder() {
		//查询所有未付款订单，判断时间是否超时(1 day),超时就关闭交易
		OrderStatus orderStatusExemple = new OrderStatus();
		orderStatusExemple.setOrderStatus(OrderStatusEnum.WAIT_PAY.trype);
		Example<OrderStatus> example = Example.of(orderStatusExemple);
		List<OrderStatus> orderStatus = orderStatusRepository.findAll(example);
		orderStatus.stream().forEach(os->{
			//获得订单创建时间
			Date createdTime = os.getCreatedTime();
			//和当前时间对比
			int days = DateUtil.daysBetween(createdTime, new Date());
			if(days >= 1) {
				//超过一天，关闭订单
				doCloseOrder(os.getId());
			}
		});
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	private void doCloseOrder(Long oderId) {
		OrderStatus orderStatus = orderStatusRepository.getOne(oderId);
		orderStatus.setOrderStatus(OrderStatusEnum.CLOSE.trype);
		orderStatus.setCloseTime(new Date());
		orderStatusRepository.save(orderStatus);
	}

	@Override
	public Orders checkUserOrder(Long userId, Long orderId) {
		Orders order = new Orders();
		order.setId(orderId);
		order.setUserId(userId);
		Example<Orders> example = Example.of(order);
		return orderRepository.findOne(example).orElse(null);
	}

}