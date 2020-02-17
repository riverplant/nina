package org.nina.security.Oauth2.Social;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
/**
 * 
 * @author riverplant
 *
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter{
    @Autowired
	private DataSource dataSource;

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		//将系统的用户和第三方用的关系存入数据库
		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator,null);
		repository.setTablePrefix("pzx_");
		return repository;
	}
    
    
}
