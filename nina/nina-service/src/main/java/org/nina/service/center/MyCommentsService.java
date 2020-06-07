package org.nina.service.center;

import org.nina.domain.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MyCommentsService {
  
	Page<OrderDetail> queryPendingComment(Long orderId, Pageable pageable);
}
