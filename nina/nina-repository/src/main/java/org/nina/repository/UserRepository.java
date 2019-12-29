package org.nina.repository;

import java.util.List;
import org.nina.domain.User;
import org.nina.repository.support.NinaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends NinaRepository<User>{
	
	List<User> findByUsernameLikeAndEmailOrderByUsernameDesc(String username,String email);

	//List<User> findByUsernameLike(String username, SortComparator sort);

	Page<User> findByUsernameLike(String username, Pageable sort);
	/**
	 * 
	 * @param username
	 * @param email
	 * @param sort:new PageRequest(0,10)
	 * @return
	 */
//	@Query("from User u where u.username like ?1 and u.email = ?2 order by b.username desc")
//	Page<User> queryByCondition(String username,String email,Pageable sort);
	
	
}
