package org.nina.service;

import java.util.Date;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;

import org.nina.commons.aop.ServiceLog;
import org.nina.commons.aop.UserLoginLog;
import org.nina.commons.enums.Sex;
import org.nina.commons.utils.DateUtil;
import org.nina.commons.utils.MD5Utils;
import org.nina.domain.User;
import org.nina.dto.UserInfo;
import org.nina.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
	private static final String USER_FACE = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";

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
			Predicate p1 =	criteriaBuilder.equal(root.get("username"), username);
			//Predicate p2 = criteriaBuilder.equal(root.get("username").get(""), "nina");
			Predicate p2 = criteriaBuilder.equal(root.get("email"), email);
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
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public boolean queryUsernameIsExist(String username) {
		User user = new User();
		user.setUsername(username);
		Example<User> userExemple = Example.of( user);
		return userRepository.exists(userExemple);
	}
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public UserInfo createUser(@Valid UserInfo userInfo) {
		User user = new User();
		BeanUtils.copyProperties(userInfo, user);
		try {
			String password = MD5Utils.getMD5Str(userInfo.getPassword());
			user.setPassword(password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user.setFace(USER_FACE);
		user.setSex(Sex.SECRET);
		user.setBirthday(DateUtil.stringToDate("1900-01-01"));
		user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());
        userRepository.save(user);
        userInfo.setId(user.getId());
		return userInfo;
	}
	@Override
	@UserLoginLog//通过该注解触发aop
	@Transactional(propagation = Propagation.SUPPORTS)
	public UserInfo queryUserForLogin(String username, String password) {
		Specification<User> spec = new Specification<User>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
			Predicate p1 =	criteriaBuilder.equal(root.get("username"), username);
			Predicate p2 = criteriaBuilder.equal(root.get("password"), password);
			Predicate p3 = criteriaBuilder.and(p1,p2);	
			return p3;
			};
		};
		User user = userRepository.findOne(spec).orElse(null);
		UserInfo userInfo = new UserInfo();
		if(user!=null) {
			BeanUtils.copyProperties(user, userInfo);
			return userInfo;
		}
		return null;
	}

}
