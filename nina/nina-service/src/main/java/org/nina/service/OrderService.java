package org.nina.service;

import org.nina.domain.OrderStatus;
import org.nina.domain.Orders;
import org.nina.dto.vo.OrderVO;
import org.nina.dto.vo.SubmitOrderVO;

public interface OrderService {
    /**
     * 创建订单相关信息
     * @param submitOrderVO
     */
	OrderVO createOrder(SubmitOrderVO submitOrderVO);
	/**
	 * 修改订单状态
	 * @param orderId
	 * @param orderStatus
	 */
	void updateOrderStatus(Long orderId, Integer orderStatus);
	/**
	 * 查询订单状态
	 * @param orderId
	 * @return
	 */
	OrderStatus queryOrderStatusInfo(Long orderId);
	/**
	 * 关闭超时未支付订单
	 */
	public void closeOrder();
	/**
	 * 验证用户和订单关联
	 * @param userId
	 * @param orderId
	 */
	Orders checkUserOrder(Long userId, Long orderId);
	
}
