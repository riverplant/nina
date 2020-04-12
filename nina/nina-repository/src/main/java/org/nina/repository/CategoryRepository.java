package org.nina.repository;

import java.util.List;

import org.nina.domain.Category;
import org.nina.dto.vo.CategoryVO;
import org.nina.dto.vo.NewItemsVO;
import org.nina.repository.support.NinaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends NinaRepository<Category> {
	
	@Query(value = "select f.river_id as id,"
			          + "f.`name` as `name`,"
			          + "f.type as type,"
			          + "f.father_id as fatherId," 
			          + "c.river_id as subId,"
			          + "c.`name` as `subName`,"
			          + "c.type as subType,"
			          + "c.father_id as subFatherId " 
			          +"FROM river_category f "
			         + "LEFT JOIN river_category c ON f.river_id = c.father_id "
			         + "WHERE c.father_id = ?1", nativeQuery = true)
	List<Object> querySubCatalog(Long id);
	
	@Query(value = "select f.id as rootCatId,"
			          + "f.`name` as `rootCatName`,"
			          + "f.slogan as slogan,"
			          + "f.cat_image as catImage,"
			          + "f.bg_color as bgColor "
			          + "i.id as itemId,"
			          + "i.item_name as `itemName`,"
			          + "ii.url as itemUrl,"
			          + "i.create_time as createTime " 
			          + "FROM river_category f "
	                  + "LEFT JOIN river_items i ON f.id = i.root_cat_id "
	                  + "LEFT JOIN river_Items_img ii ON i.id = ii.item_id "
	                  + "WHERE f.type = 1 AND i.root_cat_id = ?1"
	                   + "AND ii.is_main = true ORDER BY i.createTime DESC LIMIT 0,6", nativeQuery = true)
	List<NewItemsVO> getSixNewItemsLazy(Long id);
}
