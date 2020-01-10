package org.nina.commons.utils;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author jli
 *
 */
public class LogUtils {
	private static Logger LOGGER = LoggerFactory.getLogger(LogUtils.class);
	/**
	 * 记录参数
	 * 
	 * @param arg
	 * @param string
	 */
	public static void printObj(Object arg, String prefix) {
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
