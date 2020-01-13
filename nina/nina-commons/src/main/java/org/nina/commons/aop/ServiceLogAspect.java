package org.nina.commons.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.nina.commons.utils.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 全局日志切片
 * 
 * @author riverplant
 *
 */
@Component
@Aspect
public class ServiceLogAspect {
	private static Logger LOGGER = LoggerFactory.getLogger(ServiceLogAspect.class);

	/**
	 * AOP: 1.前置通知:在方法调用之前执行 2.后置通知:在方法正常调用之后执行 3.环绕通知:在方法调用之前方法正常调用之后,都分别可以执行的通知
	 * 4.异常通知:在方法调用中发生异常,则通知 5.最终通知:在方法调用之后执行
	 */

	/**
	 * 
	 * @param pjp:切片拦截方法的封装类
	 * @return
	 * @throws Throwable service..*:service下面所有的子包(service.*:代表只包含一层子包)
	 *                   service..*.*: service下面所有的子包中的任何一个类
	 */
	// 通过注解来触发该切片，当方法上有该注解就会触发该切片方法
	@Pointcut("@annotaion(org.nina.commons.aop.ServiceLog)")
	// @Around("execution(* org.nina.service..*.*(..))")
	public Object logServiceInvoke(ProceedingJoinPoint pjp) throws Throwable {

		return doLog(pjp);
	}

	protected Object doLog(ProceedingJoinPoint pjp) throws Throwable {
		// 正式公司中一般使用LOGGER.isDebugEnabled()
		if (LOGGER.isInfoEnabled()) {
			// pjp.getSignature().toLongString():获得调用服务名称
			LOGGER.info("****调用服务{}.{}", pjp.getTarget().getClass(), pjp.getSignature().toLongString() + "****");
			// pjp.getArgs():获得该方法的所有服务参数
			for (Object arg : pjp.getArgs()) {
				LogUtils.printObj(arg, "服务参数:");
			}
			try {
				// 记录开始时间
				long begin = System.currentTimeMillis();
				// pjp.proceed():执行被切入的方法
				Object retVal = pjp.proceed();
				LogUtils.printObj(retVal, "返回结果:");
				// 记录结束时间
				long waitTime = System.currentTimeMillis() - begin;
				if (waitTime > 3000) {
					// 等待时间超过三秒
					LOGGER.error("========执行结束,耗时{}毫秒=========", waitTime);
				} else if (waitTime > 2000) {
					// 等待时间超过三秒
					LOGGER.warn("========执行结束,耗时{}毫秒=========", waitTime);
				} else {
					LOGGER.info("========执行结束,耗时{}毫秒=========", waitTime);
				}
				return retVal;
			} catch (Throwable e) {
				LOGGER.info("抛出异常", e);
				throw e;
			} finally {
				LOGGER.info("****调用服务结束******");
			}
		}
		return pjp.proceed();
	}

}
