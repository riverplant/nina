package org.nina.security.config;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * 登录认证成功处理器
 * 
 * @author riverplant
 *
 */
@Component("ninaAuthenticationSuccessHandler")
public class NinaAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	private static Logger Log = LoggerFactory.getLogger(NinaAuthenticationSuccessHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		/**
		 * 记录用户登录日志
		 */
		NinaUserdetails userDetails = (NinaUserdetails) authentication.getPrincipal();
		Log.info(userDetails.getUsername() + "login at" + new Date());
		System.out.println(userDetails);
		super.onAuthenticationSuccess(request, response, authentication);
		;
	}

}
