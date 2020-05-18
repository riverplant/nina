package com.riverplant.payCenter.service;

import com.riverplant.payCenter.domain.Users;

public interface UserService {

	Users queryUserInfo(String imoocUserId, String password);

}
