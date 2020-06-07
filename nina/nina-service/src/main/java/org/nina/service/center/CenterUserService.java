package org.nina.service.center;

import javax.validation.Valid;

import org.nina.dto.UserInfo;

public interface CenterUserService {
    /**
     * 根据用户Id查询用户信息
     * @param userId
     * @return
     */
	public UserInfo queryUserInfo(Long userId);

	public UserInfo updateUserInfo(Long userId, @Valid UserInfo userInfo);
	/**
	 * 用户头像更新
	 * @param userId
	 * @param faceUrl
	 * @return
	 */
	public UserInfo updateUserFace(Long userId, String faceUrl);
}
