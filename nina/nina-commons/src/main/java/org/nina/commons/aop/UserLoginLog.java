package org.nina.commons.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 提供给切片AOP使用的注解
 * @author riverplant
 *@Target({ElementType.TYPE,ElementType.METHOD}):该注解可以写在类上和方法上
 *@Retention(RetentionPolicy.RUNTIME):编译时候保留
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserLoginLog {

}
