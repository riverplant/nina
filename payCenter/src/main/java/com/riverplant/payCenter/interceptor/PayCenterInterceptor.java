package com.riverplant.payCenter.interceptor;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.nina.commons.utils.DateUtil;
import org.nina.commons.utils.JsonUtils;
import org.nina.commons.utils.NinaJsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.riverplant.payCenter.domain.Users;
import com.riverplant.payCenter.service.UserService;
/**
 * 
 * @author riverplant
 *
 */
public class PayCenterInterceptor implements HandlerInterceptor {
	@Autowired
	private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String imoocUserId = request.getHeader("imoocUserId");
		String password = request.getHeader("password");
		if (StringUtils.isNotBlank(imoocUserId) && StringUtils.isNotBlank(password)) {

			// 请求数据库查询用户是否存在
			Users  user = userService.queryUserInfo(imoocUserId, password);
			if (user == null) {
				returnErrorResponse(response, NinaJsonResult.erorTokenMsg("用户id或密码不正确！"));
				return false;
			}

			Date endDate = user.getEndDate();
			Date nowDate = new Date();

			int days = DateUtil.daysBetween(nowDate, endDate);
			if (days < 0) {
				returnErrorResponse(response, NinaJsonResult.erorTokenMsg("该账户授权访问日期已失效！"));
				return false;
			}

			// 判断限制访问次数
			/*Integer limit = user.getLimit();
			if (limit != -1) {
				// -1 代表访问无限次
			}*/

		} else {
			returnErrorResponse(response, NinaJsonResult.erorTokenMsg("请在header中携带支付中心所需的用户id以及密码！"));
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
	public void returnErrorResponse(HttpServletResponse response, NinaJsonResult result)
			throws IOException, UnsupportedEncodingException {
		OutputStream out=null;
		try{
		    response.setCharacterEncoding("utf-8");
		    response.setContentType("application/json");
		    out = response.getOutputStream();
		    out.write(JsonUtils.objectToJson(result).getBytes("utf-8"));
		    out.flush();
		} finally{
		    if(out!=null){
		        out.close();
		    }
		}
	}
}
