package org.nina.repository;

import org.nina.domain.OrderStatus;
import org.nina.repository.support.NinaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderStatusRepository extends NinaRepository<OrderStatus>{
	@Query(" from OrderStatus os where os.orderId = ? ")	
	OrderStatus getByOrderId(Long orderId);
}
