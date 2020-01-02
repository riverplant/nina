package org.nina.commons.aop;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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
	 * 
	 * @param pjp:切片拦截方法的封装类
	 * @return
	 * @throws Throwable
	 *             service..*:service下面所有的子包(service.*:代表只包含一层子包) service..*.*:
	 *             service下面所有的子包中的任何一个类
	 */
	//通过注解来触发该切片，当方法上有该注解就会触发该切片方法
	@Around("@annotaion(org.nina.commons.aop.ServiceLog)")
	//@Around("execution(* org.nina.service..*.*(..))")
	public Object logServiceInvoke(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("方法被调用");
		return doLog(pjp);
	}

	protected Object doLog(ProceedingJoinPoint pjp) throws Throwable {
		// 正式公司中一般使用LOGGER.isDebugEnabled()
		if (LOGGER.isInfoEnabled()) {
			// pjp.getSignature().toLongString():获得调用服务名称
			LOGGER.info("****调用服务" + pjp.getSignature().toLongString() + "****");
			// pjp.getArgs():获得该方法的所有服务参数
			for (Object arg : pjp.getArgs()) {
				printObj(arg, "服务参数:");
			}
			try {
				// pjp.proceed():执行被切入的方法
				Object retVal = pjp.proceed();
				printObj(retVal, "返回结果:");
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

	/**
	 * 记录参数
	 * 
	 * @param arg
	 * @param string
	 */
	private void printObj(Object arg, String prefix) {
		if (arg != null) {
			if (arg.getClass().isArray()) {
				if (ArrayUtils.isNotEmpty((Object[]) arg)) {
					Object[] args = (Object[]) arg;
					for (Object object : args) {
						printObj(object, prefix);
					}
				}
			} else if (arg instanceof Collection) {
				if (CollectionUtils.isNotEmpty((Collection) arg)) {
					Collection collection = (Collection) arg;
					for (Object object : collection) {
						printObj(object, prefix);
					}
				}
			}
            //ClassUtils.isPrimitiveOrWrapper:判断是否是一个基本类型,或者是一个包装类型
			if (ClassUtils.isPrimitiveOrWrapper(arg.getClass())) {
				LOGGER.info(prefix + arg.toString());
			} else if (arg instanceof String) {
				LOGGER.info(prefix + (String) arg);
			} else {
				//ReflectionToStringBuilder:反射
				LOGGER.info(prefix + ReflectionToStringBuilder.toString(arg));
			}
		}else {
			LOGGER.info(prefix + "null");
		}
	}
}
