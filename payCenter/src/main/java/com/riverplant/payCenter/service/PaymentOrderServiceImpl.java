package com.riverplant.payCenter.service;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.nina.commons.enums.OrderStatusEnum;
import org.nina.commons.enums.YesOrNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.riverplant.payCenter.domain.PayOrders;
import com.riverplant.payCenter.domain.vo.PayOrdersVO;
import com.riverplant.payCenter.repository.PayOrderRepository;

@Service
@Transactional
public class PaymentOrderServiceImpl implements PaymentOrderService {
    @Autowired private PayOrderRepository payOrderRepository;
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public boolean createPaymentOrder(PayOrdersVO payOrdersVO){
		PayOrders paymentOrder = new PayOrders();
		try {
			BeanUtils.copyProperties(paymentOrder, payOrdersVO);
			paymentOrder.setComeFrom("");
			paymentOrder.setPayStatus(OrderStatusEnum.WAIT_PAY.trype);
			paymentOrder.setIsDelete(YesOrNo.NO.trype);
			payOrderRepository.save(paymentOrder);
			return true;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}		
		return false;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public PayOrders queryOrderInfo(String merchantOrderId, String merchantUserId) {
		PayOrders payOrder = new PayOrders();
		payOrder.setMerchantOrderId(Long.valueOf(merchantOrderId));
		payOrder.setMerchantUserId(Long.valueOf(merchantUserId));
		Example<PayOrders> example = Example.of(payOrder);
		return payOrderRepository.findOne(example).orElse(null);
	}
	/**
	 * 
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public PayOrders queryOrderByStatus(String merchantOrderId, String merchantUserId, OrderStatusEnum payStatus) {
		PayOrders payOrder = new PayOrders();
		payOrder.setMerchantOrderId(Long.valueOf(merchantOrderId));
		payOrder.setMerchantUserId(Long.valueOf(merchantUserId));
		payOrder.setPayStatus(payStatus.trype);
		Example<PayOrders> example = Example.of(payOrder);
		return payOrderRepository.findOne(example).orElse(null);
	}
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public String updateOrderPaid(String merchantOrderId, Integer paidAmount, OrderStatusEnum payStatus) {
		PayOrders payOrder = new PayOrders();
		payOrder.setMerchantOrderId(Long.valueOf(merchantOrderId));
		Example<PayOrders> example = Example.of(payOrder);
		PayOrders payOrderUpdate = payOrderRepository.findOne(example).orElse(null);
		if(payOrderUpdate != null) {
			payOrderUpdate.setAmount(paidAmount);
			payOrderUpdate.setPayStatus(payStatus.trype);
			payOrderRepository.save(payOrderUpdate);
			return payOrderUpdate.getReturnUrl();
		}
		return null;
		
		
		
		
	}

}
