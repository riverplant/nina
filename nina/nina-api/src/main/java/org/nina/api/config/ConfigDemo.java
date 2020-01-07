package org.nina.api.config;

import java.util.Set;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * @author riverplant
 *
 */
@Component
public class ConfigDemo implements InitializingBean{
    /**
     * 可以读取到配置文件里配置的aaa.bbb.ccc的值
     * 当读取配置失败默认使用Hello
     */
	@Value("${aaa.bbb.ccc:Hello}")
	private String test;
	

	public String getTest() {
		return test;
	}


	public void setTest(String test) {
		this.test = test;
	}

	@Autowired TestProperties testProperties;
    /**
     * 当注入和读取配置文件的值之后调用该方法
     */
	@Override
	public void afterPropertiesSet() throws Exception {
		/**
		 * ReflectionToStringBuilder:通过反射将对象打印成字符串
		 * ToStringStyle.MULTI_LINE_STYLE:使用多行的打印样式
		 */
		System.out.println(ReflectionToStringBuilder.toString(testProperties,ToStringStyle.MULTI_LINE_STYLE));
		Set<String>keys = testProperties.getUsers().keySet();
		for(String key : keys) {
			/**
			 * key:a.password  value:123
			 * key:a.age       value:18
			 * ....
			 */
			System.out.println(key);
			System.out.println(testProperties.getUsers().get(key));
		}
	}
}
