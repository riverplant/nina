package org.nina.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 
 * @author riverplant
 *
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;
	@Autowired
	private NinaAuthenticationSuccessHandler ninaAuthenticationSuccessHandler;
	@Autowired
	private NinaAuthenticationFaildHandler ninaAuthenticationFaildHandler;
    
	/**
	 * 配置身份认证
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceImpl)
		.passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()//加入了UsernamePasswordAuthenticationFilter
		.loginPage("/login.html")
		.loginProcessingUrl("/auth")
		//可以通过配置修改
		.usernameParameter("username")
		.passwordParameter("password")
		.successHandler(ninaAuthenticationSuccessHandler)
		.failureHandler(ninaAuthenticationFaildHandler)
		.and()
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/index/*","/login.html","/auth").permitAll()
	// .antMatchers(HttpMethod.GET).permitAll()
		.anyRequest().authenticated();
	}

}
