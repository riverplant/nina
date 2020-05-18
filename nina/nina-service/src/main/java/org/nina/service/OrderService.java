package org.nina.service;

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
}
