package org.nina.service.center;
import org.nina.domain.OrderDetail;
import org.nina.repository.OrderDetailRepository;
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

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Page<OrderDetail> queryPendingComment(Long orderId, Pageable pageable) {
		OrderDetail de = new OrderDetail();
		de.setOrderId(orderId);
		Example<OrderDetail> example = Example.of(de);
		return orderDetailRepository.findAll(example, pageable);
	}

}