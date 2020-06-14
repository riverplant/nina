package org.nina.service.center;

import java.util.List;

import org.nina.domain.OrderDetail;
import org.nina.dto.vo.center.OrderItemsCommentVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MyCommentsService {
  
	Page<OrderDetail> queryPendingComment(Long orderId, Pageable pageable);
    /**
     * 保存评论
     * @param userId
     * @param orderId
     * @param commentList
     */
	void saveComments(Long userId, Long orderId, List<OrderItemsCommentVO> commentList);
}
