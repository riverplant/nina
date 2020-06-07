package org.nina.service.center;

import org.nina.dto.vo.center.MyOrdersVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MyOrderService {
  
	Page<MyOrdersVO> queryMyOrders(Long userId,
			                      Integer orderStatus,
			                      Pageable pageable);
}
