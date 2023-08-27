package com.backend.topcariving.global.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class DatasourceConfig {

	public static final String MASTER_DATA_SOURCE = "MASTER";
	public static final String SLAVE_DATA_SOURCE = "SLAVE";

	@Bean
	@Qualifier(MASTER_DATA_SOURCE)
	@ConfigurationProperties(prefix = "spring.datasource.master")
	public DataSource masterDataSource() {
		return DataSourceBuilder
			.create().build();
	}

	@Bean
	@Qualifier(SLAVE_DATA_SOURCE)
	@ConfigurationProperties(prefix = "spring.datasource.slave")
	public DataSource slaveDataSource() {
		return DataSourceBuilder
			.create().build();
	}

	@Bean
	public DataSource routingDataSource(
		@Qualifier(MASTER_DATA_SOURCE) DataSource masterDataSource,
		@Qualifier(SLAVE_DATA_SOURCE) DataSource slaveDataSource
	) {
		RoutingDataSource routingDataSource = new RoutingDataSource();

		HashMap<Object, Object> datasourceMap = new HashMap<>();
		datasourceMap.put(MASTER_DATA_SOURCE, masterDataSource);
		datasourceMap.put(SLAVE_DATA_SOURCE, slaveDataSource);

		routingDataSource.setTargetDataSources(datasourceMap);
		routingDataSource.setDefaultTargetDataSource(masterDataSource);

		return routingDataSource;
	}

	@Bean
	@Primary
	public DataSource dataSource() {
		DataSource dataSource = routingDataSource(masterDataSource(), slaveDataSource());
		return new LazyConnectionDataSourceProxy(dataSource);
	}
}
