package org.nina.repository;

import java.util.List;

import org.nina.domain.ItemsSpec;
import org.nina.dto.vo.ShopcartVO;
import org.nina.repository.support.NinaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface ItemsSpecRepository extends NinaRepository<ItemsSpec>{
    @Query(value="SELECT t_items.id as itemId, t_items.item_name as itemName,t_items_img.url as itemImgUrl,"
    		+ "t_items_spec.id as specId,t_items_spec.name as specName,t_items_spec.price_discount as priceDiscount,t_items_spec.price_normal as priceNormal"
    		+ "FROM items_spec t_items_spec LEFT JOIN river_items t_items ON t_items.id = t_items_spec.item_id LEFT JOIN river_items_img t_items_img ON t_items_img.item_id =  t_items.id"
    		+ "WHERE t_items_img.is_main = 1 AND t_items_spec.id IN (:specIds)"
    		,nativeQuery = true)
	List<ShopcartVO> queryItemsBySpecIds(@Param("specIds") List<Long> specIds);
}
