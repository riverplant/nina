package org.nina.service.center;
import org.nina.dto.vo.center.MyOrdersVO;
import org.nina.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MyOrderServiceImpl<V> implements MyOrderService {

	@Autowired private OrderRepository orderRepository;

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Page<MyOrdersVO> queryMyOrders(Long userId, Integer orderStatus, Pageable pageable) {
		return orderRepository.queryOrdersByUserId(userId,orderStatus,pageable);
	}

}