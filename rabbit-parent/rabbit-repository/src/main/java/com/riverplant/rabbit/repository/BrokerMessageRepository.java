package com.riverplant.rabbit.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.riverplant.rabbit.domain.BrokerMessage;
import com.riverplant.rabbit.repository.support.NinaRepository;
@Repository
public interface BrokerMessageRepository extends NinaRepository<BrokerMessage>{

	
	/**
	 * 用户提交订单后修改库存，这里需要使用乐观锁
	 * @param specId
	 * @param buyCounts
	 */
	@Query(value="update items_spec "
					+ "set stock = stok - ?1 "
					+ "where id = ?2"
					+ "and stock >= ?1"
					,nativeQuery = true)
	int decreaseItemSpecStock(Long specId, Integer buyCounts);

	@Query(value="update broker_message "
			+ "set status = ?3 "
			+ "where message_id = ?1"
			+ "and status = ?2"
			+ "and updatedTime = ?4"
			,nativeQuery = true)
	void changeBrokerMessageStatus(String messageId, String oldStatus, String newStatus, Date date);
}
