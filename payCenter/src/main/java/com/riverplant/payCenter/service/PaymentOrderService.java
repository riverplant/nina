package com.riverplant.payCenter.service;

import org.nina.commons.enums.OrderStatusEnum;

import com.riverplant.payCenter.domain.PayOrders;
import com.riverplant.payCenter.domain.vo.MerchantOrdersVO;

public interface PaymentOrderService {

	boolean createPaymentOrder(MerchantOrdersVO payOrdersVO);

	PayOrders queryOrderInfo(String merchantOrderId, String merchantUserId);

	PayOrders queryOrderByStatus(String merchantOrderId, String merchantUserId, OrderStatusEnum waitPay);

	String updateOrderPaid(String merchantOrderId, Integer paidAmount, OrderStatusEnum payStatus);
}
