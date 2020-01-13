package org.nina.repository;

import java.util.List;

import org.nina.domain.Category;
import org.nina.repository.support.NinaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends NinaRepository<Category> {
	
	@Query(value = "select f.id as id, " + "f.name as name," + "f.type as type," + "f.fatherId as fatherId, "
			+ "c.id as subId," + "c.name as subName," + "c.type as subType," + "c.fatherId as subFatherId"
			+ "FROM Category f " + "LEFT JOIN " + "Category c " + "ON f.id = c.fatherId " + "WHERE f.fatherId = ?1")
	List<Category> querySubCatalog(Long id);
}
