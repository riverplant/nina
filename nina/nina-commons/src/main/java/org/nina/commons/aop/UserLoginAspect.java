package org.nina.commons.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.nina.commons.utils.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
/**
 * 
 * @author jli
 * 用户登录切片
 */
@Component
@Aspect
public class UserLoginAspect {
	private static Logger LOGGER = LoggerFactory.getLogger(UserLoginAspect.class);
	
	/**
	 * 
	 * @param pjp:切片拦截方法的封装类
	 * @return
	 * @throws Throwable
	 *             service..*:service下面所有的子包(service.*:代表只包含一层子包) service..*.*:
	 *             service下面所有的子包中的任何一个类
	 */
	//通过注解来触发该切片，当方法上有该注解就会触发该切片方法
	@Around("@annotaion(org.nina.commons.aop.UserLoginLog)")
	//@Around("execution(* org.nina.service..*.*(..))")
	public Object logServiceInvoke(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("开始验证用户登录..........");
		return doLog(pjp);
	}

	protected Object doLog(ProceedingJoinPoint pjp) throws Throwable {
		// 正式公司中一般使用LOGGER.isDebugEnabled()
		if (LOGGER.isInfoEnabled()) {
			try {
				Object retVal = pjp.proceed();
				LogUtils.printObj("用户登录成功", "返回结果:");
				return retVal;
			} catch (Throwable e) {
				LOGGER.info("抛出异常,用户登录失败", e);
				throw e;
			} finally {
				LOGGER.info("****调用服务结束******");
			}
		}
		return pjp.proceed();
	}
}
