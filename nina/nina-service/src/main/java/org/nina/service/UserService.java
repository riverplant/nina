package org.nina.service;

import java.util.Optional;

import org.nina.domain.User;
import org.nina.dto.UserInfo;
import org.springframework.data.domain.Pageable;

public interface UserService {

	 Optional<User> queryByCondition(String username,String email,Pageable sort);
	
	 public UserInfo getInfo(Long id);
}
