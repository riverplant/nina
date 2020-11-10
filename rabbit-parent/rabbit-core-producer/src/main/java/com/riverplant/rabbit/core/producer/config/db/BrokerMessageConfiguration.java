package com.riverplant.rabbit.core.producer.config.db;

import org.springframework.core.io.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
/**
 * $BrokerMessageConfiguration  执行sql脚本，创建数据库
 * @author riverplant
 *
 */
//@Configuration
public class BrokerMessageConfiguration {

	@Autowired private DataSource rabbitProducerDataSource;
	
	@Value("classpath:rabbit-producer-message-schema.sql")
	private Resource schemaScript;
	
	@Bean
	public DataSourceInitializer initDataSourceInitializer() {
		System.err.println("------------rabbitProducerDataSource:"+rabbitProducerDataSource);
		final DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(rabbitProducerDataSource);
		//执行sql
		initializer.setDatabasePopulator(databasePopulator());
		return initializer;
	}

	private DatabasePopulator databasePopulator() {
		final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(schemaScript);
		return populator;
	}
}
