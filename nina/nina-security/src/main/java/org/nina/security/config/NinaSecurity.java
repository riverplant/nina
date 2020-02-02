package org.nina.security.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * 
 * @author riverplant
 *
 */
@Component("ninaSecurity")
public class NinaSecurity {
	/**
	 * 
	 * @param authentication
	 * @param request
	 * @return true:可以访问， false:不可以访问
	 */
	public boolean check(Authentication authentication, HttpServletRequest request) {
		/**
		 * 通过voter来判断
		 */
		System.out.println(request.getRequestURI());
		Object principal = authentication.getPrincipal();
		if (principal != null && principal instanceof UserDetails) {
			/**
			 * 已经成功登录
			 */
          System.out.println(((UserDetails)principal).getAuthorities());
          /**
           * 这里可以拿到用户的所有权限，也可以拿到服务的路径，request.getRequestURI()
           * 可以通过查询数据库确定该用户是否有访问该路径服务的权限
           */
		}
		return true;
	}
}
