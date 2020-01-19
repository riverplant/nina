package org.nina.security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
@EnableWebSecurity
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
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/index/*", "/login.html", "/auth").permitAll()
				// .antMatchers(HttpMethod.GET).permitAll()
				.anyRequest().authenticated();
	}

}
