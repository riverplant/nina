package org.nina.security.config;

import org.apache.commons.lang3.StringUtils;
import org.nina.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

/**
 * 
 * @author riverplant
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService, SocialUserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return loadUserByUserId(username);
	}

	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
		/**
		 * 使用userRepository读取数据库对用户进行认证
		 */
		if (StringUtils.equals(userId, "nina"))
			;
		String password = new BCryptPasswordEncoder().encode("123456");
		// 创建用户权限
		AuthorityUtils.createAuthorityList("admin", "root");
		/**
		 * 可以通过UserDetails接口的实现类来自定义权限列表
		 */
		return new SocialUser("nina", password, AuthorityUtils.createAuthorityList("admin", "root"));
	}

}
