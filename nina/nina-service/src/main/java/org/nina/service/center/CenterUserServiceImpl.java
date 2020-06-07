package org.nina.service.center;

import javax.validation.Valid;

import org.nina.domain.User;
import org.nina.dto.UserInfo;
import org.nina.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CenterUserServiceImpl implements CenterUserService {
    @Autowired private UserRepository userRepository;
	
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
	public UserInfo queryUserInfo(Long userId) {
    	User user = userRepository.getOne(userId);
    	UserInfo userInfo = new UserInfo();
    	BeanUtils.copyProperties(user, userInfo);
		return userInfo;
	}
    
    @Transactional(propagation = Propagation.REQUIRED)
	@Override
	public UserInfo updateUserInfo(Long userId, @Valid UserInfo userInfo) {
    	User user = userRepository.findById(userId).orElse(null);
    	if(user != null) {
    		BeanUtils.copyProperties(userInfo, user);
    		userRepository.save(user);
    		return queryUserInfo(userId);
    	}
    	return null;
	}

	@Override
	public UserInfo updateUserFace(Long userId, String faceUrl) {
		User user = userRepository.findById(userId).orElse(null);
    	if(user != null) {
    		user.setFace(faceUrl);
    		userRepository.save(user);
    		return queryUserInfo(userId);
    	}
    	return null;
	}

}
