package org.nina.api.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
@Component
public class NinaInterceptor implements HandlerInterceptor{
	/**
	 * RestAPI处理完成后调用
	 * Object handler:当前调用RestAPI请求的Java方法的抽象
	 * Exception ex:抛出异常的时候有值
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("afterCompletion服务处理耗时:"+(new Date().getTime() - (Long)request.getAttribute("startTime"))+"毫秒");
		if(ex != null) {
			System.out.println("ex:"+ex.getMessage());
		}
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	/**
	 * RestAPI成功返回后调用
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		System.out.println("postHandle服务处理耗时:"+(new Date().getTime() - (Long)request.getAttribute("startTime"))+"毫秒");
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	/**
	 * RestAPI调用前
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//获得当前调用RestAPI请求的Java类的名字
		System.out.println(((HandlerMethod)handler).getBean().getClass().getName());
		//获得当前调用RestAPI请求的Java类的方法的名字
		System.out.println(((HandlerMethod)handler).getMethod().getName());
		//记录请求进来的时间
		request.setAttribute("startTime", new Date().getTime());
		MDC.put("userId", "123456");
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	
}
