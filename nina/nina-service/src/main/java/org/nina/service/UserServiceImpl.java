package org.nina.service;

import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.nina.commons.aop.ServiceLog;
import org.nina.domain.User;
import org.nina.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
/**
 * 通过定义transactionManager.commit来保持上下文的一致性
 * @author riverplant
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired private UserRepository userRepository;
	//@Autowired private PlatformTransactionManager transactionManager;
	@Override
	@ServiceLog//通过该注解触发aop
	public Optional<User> queryByCondition(String username, String email, Pageable sort) {
		//TransactionStatus statu = transactionManager.getTransaction(new DefaultTransactionDefinition());
		Specification<User> spec = new Specification<User>() {
			private static final long serialVersionUID = 1L;
			/**root:User的抽象
			 * query:封装sql
			 * criteriaBuilder:创建Predicate
			 */
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				/**arg0:接收一个表达式，由root获取
				 * arg1：具体的值
				 */
			Predicate p1 =	criteriaBuilder.equal(root.get("username"), "nina");
			//Predicate p2 = criteriaBuilder.equal(root.get("username").get(""), "nina");
			Predicate p2 = criteriaBuilder.equal(root.get("password"), "123456");
			Predicate p3 = criteriaBuilder.and(p1,p2);	
			/**
			 * 设置动态查询的抓取策略
			 * 通过left join 来连接table2获取table2的相关值
			 */
			//root.fetch("table2",JoinType.LEFT);
			return p3;
			};
		
		};
		//transactionManager.commit(statu);
		return userRepository.findOne(spec);
	}

}
