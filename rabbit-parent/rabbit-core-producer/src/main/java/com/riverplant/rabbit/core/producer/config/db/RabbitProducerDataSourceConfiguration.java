package com.riverplant.rabbit.core.producer.config.db;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

//@Configuration
@PropertySource({"classpath:rabbit-producer-message.properties"})
public class RabbitProducerDataSourceConfiguration {

	private static  Logger LOGGER = LoggerFactory.getLogger(RabbitProducerDataSourceConfiguration.class);
	
	@Value("${spring.datasource.type}")
	private Class<? extends DataSource> dataSourceType;
	
	@Bean(name = "rabbitProducerDataSource")
	@Primary//主数据库
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource rabbitProducerDataSource() throws SQLException{
		DataSource rabbitProducerDataSource = DataSourceBuilder.create().type(dataSourceType).build();
		LOGGER.info("============== rabbitProduceDataSource ： {} ========", rabbitProducerDataSource);		
		return rabbitProducerDataSource;
	}
	
	public DataSourceProperties primaryDataSourceProperties() {
		return new DataSourceProperties();
	}
	
	public DataSource primaryDataSource() {
		return primaryDataSourceProperties().initializeDataSourceBuilder().build();
	}
}
