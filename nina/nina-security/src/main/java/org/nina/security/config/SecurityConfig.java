package org.nina.security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 * 
 * @author riverplant
 *
 */
@Configuration
//拦截http请求
@EnableWebSecurity
//开启方法授权注解,prePostEnabled模式的表达式类似于：hasAuthority('admin')
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;
	@Autowired
	private NinaAuthenticationSuccessHandler ninaAuthenticationSuccessHandler;
	@Autowired
	private NinaAuthenticationFaildHandler ninaAuthenticationFaildHandler;
    //为remember me 启用数据库记录和验证 
	@Autowired
	private DataSource datasource;//数据源由java自带
    /**
     * 自动创建persisitent_logins数据库表
     * @return
     */
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
		/**
		 * 默认设置每次系统启动都会去创建persisitent_logins数据库表
		 * 所以只要第一次创建成功就可以注销这句代码
		 */
		jdbcTokenRepository.setCreateTableOnStartup(true);//启用数据库存取token		
		jdbcTokenRepository.setDataSource(datasource);
		return jdbcTokenRepository;
	}

	/**
	 * 配置身份认证
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()// 加入了UsernamePasswordAuthenticationFilter
				.loginPage("/login.html").loginProcessingUrl("/auth")
				// 可以通过配置修改
				.usernameParameter("username").passwordParameter("password")
				.successHandler(ninaAuthenticationSuccessHandler).failureHandler(ninaAuthenticationFaildHandler).and()
				//加入RememberMeAuthenticationFilter
				.rememberMe().tokenRepository(persistentTokenRepository())//启用数据库存取token
				.tokenValiditySeconds(60)//设置token有效期(秒)
				.and()
//				.sessionManagement()
//				.invalidSessionUrl("/session.html")
//				.maximumSessions(1)//最大session数为1,用于并发控制
//				.maxSessionsPreventsLogin(true)//达到最大session数就不容许新登录了
//				.and()
//				.and()
				.csrf().disable()
				.authorizeRequests()
				/**
				 * 通过该方法设置授权投票器的种类
				 */
				//.accessDecisionManager(accessDecisionManager)
				.antMatchers("/index", "/login.html", "/auth","/session.html").permitAll()
				// .antMatchers(HttpMethod.GET).permitAll()
				.antMatchers("/test/*").access("hasRole('a') or hasRole('b')")
				//.anyRequest()
				/**
				 * ConfigAttribute:需要判断的权限规则
				 */
				//.authenticated();
				/**
				 * 相当于hasAuthority("admin"),
				 * 授权工作在FilterSecurityInterceptor中做
				 */
				//.access("hasAuthority('admin')");
				/**@ninaSecurity:自定义授权类bean
				 * Check:自定义bean中的自定义方法
				 */
				.anyRequest().access("@ninaSecurity.check(authentication,request)");
	}

}
