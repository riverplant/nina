package org.nina.security.config;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.nina.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/**
 * 
 * @author riverplant
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		/**
		 * 使用userRepository读取数据库对用户进行认证
		 */
		if(StringUtils.equals(username, "nina"));
		return new User("nina","123456",new ArrayList<GrantedAuthority>());
	}

}
