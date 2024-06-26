package org.nina.service;

import java.util.List;
import java.util.Optional;

import org.nina.domain.Address;
import org.nina.domain.User;
import org.nina.dto.AddressInfo;
import org.nina.dto.UserInfo;
import org.springframework.data.domain.Pageable;

public interface UserService {

	 Optional<User> queryByCondition(String username,String email,Pageable sort);
	 /**
	  * 判断用户名是否存在
	  * @return
	  */
	 boolean queryUsernameIsExist(String username); 
	 
	 UserInfo createUser(UserInfo userInfo);
	 //用户登录
	 UserInfo queryUserForLogin(String username, String password);
	 
	 List<Address> getUserAdrress(Long id);
	 
	 void addNewAddress( AddressInfo info, Long userId);
	 
	 void updateAddress( AddressInfo info, Long userId);
}
