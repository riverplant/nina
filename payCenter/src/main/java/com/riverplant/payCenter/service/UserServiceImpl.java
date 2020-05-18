package com.riverplant.payCenter.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.riverplant.payCenter.domain.Users;
import com.riverplant.payCenter.repository.UsersRepository;

@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {
    @Autowired private UsersRepository usersRepository;
    
    @Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Users queryUserInfo(String imoocUserId, String password) {
    	Users user = new Users();
    	user.setImoocUserId(imoocUserId);
    	user.setPassword(password);
    	Example<Users> example = Example.of(user);
		return usersRepository.findOne(example).orElse(null);
	}

}
