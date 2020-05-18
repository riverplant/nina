package org.nina.repository;

import java.util.List;

import org.nina.domain.Items;
import org.nina.repository.support.NinaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface ItemsRepository extends NinaRepository<Items>{
	/**
	 * @EntityGraph(attributePaths = "table2"):通过这个注解
	 * 声明在查询User的时候需要在同一条sql语句中关联table2
	 * 也可以直接使用定义在Items上面的抓取策略!!!!!!
	 * @param username
	 * @return
	 */
	@EntityGraph(value = "Items.fetch.category.and.carousels")
	Items findByItemName(String itemName);
	
	@Query("from Items items where items.rootCatId = ?1")
	List<Items> findByRootCatId(Long rootCatId);
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
}
