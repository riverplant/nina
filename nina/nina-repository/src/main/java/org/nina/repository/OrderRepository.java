package org.nina.repository;

import org.nina.domain.Orders;
import org.nina.dto.vo.center.MyOrdersVO;
import org.nina.repository.support.NinaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderRepository extends NinaRepository<Orders>{
	@Query(value="SELECT"
			+ "od.id as orderId,"
			+ "od.created_time as createdTime,"
			+ "od.pay_method as payMethod,"
			+ "od.real_pay_amount as realPayAmount"
			+ "od.post_amount as postAmount,"
			+ "od.is_comment as isComment"
			+ "os.order_status as orderStatus,"
			+ "ods.item_id as itemId,"
			+ "ods.item_name as itemName,"
			+ "ods.item_img as itemImg,"
			+ "ods.item_spec_name as itemSpecName,"
			+ "ods.buy_counts as buyCounts,"
			+ "ods.price as price"
			+ "FROM orders od "
			+ "LEFT JOIN order_status os on od.id = os.order_id"
			+ "LEFT JOIN order_detail ods on od.id = ods.order_id "
			+ "WHERE od.user_id = ?1 AND os.order_status = ?2 AND od.is_delete = 0 "
			+ "ORDER BY od.updated_time ASC"
			+ "	?#{#pageable}",
			countQuery="SELECT"
					+ "od.id as orderId,"
					+ "od.created_time as createdTime,"
					+ "od.pay_method as payMethod,"
					+ "od.real_pay_amount as realPayAmount"
					+ "od.post_amount as postAmount,"
					+ "od.is_comment as isComment"
					+ "os.order_status as orderStatus,"
					+ "ods.item_id as itemId,"
					+ "ods.item_name as itemName,"
					+ "ods.item_img as itemImg,"
					+ "ods.item_spec_name as itemSpecName,"
					+ "ods.buy_counts as buyCounts,"
					+ "ods.price as price"
					+ "FROM orders od "
					+ "LEFT JOIN order_status os on od.id = os.order_id"
					+ "LEFT JOIN order_detail ods on od.id = ods.order_id "
					+ "WHERE od.user_id = ?1 AND os.order_status = ?2 AND od.is_delete = 0 "
					+ "ORDER BY od.updated_time ASC",
			nativeQuery = true)
	Page<MyOrdersVO> queryOrdersByUserId(Long userId, Integer orderStatus, Pageable pageable);
}
