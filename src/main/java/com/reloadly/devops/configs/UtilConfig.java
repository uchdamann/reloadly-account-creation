package com.reloadly.devops.configs;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.reloadly.devops.utilities.AppProperties;
import com.reloadly.devops.utilities.DatasourceProperties;
import com.reloadly.devops.utilities.EncryptUtil;

@Configuration
public class UtilConfig {
	@Autowired
	private AppProperties prop;
	@Autowired
	private DatasourceProperties datasourceProperties;

//	Configure encryption
	@Bean
	public EncryptUtil encryptUtil() {
		return new EncryptUtil(prop.getEncryptKey());
	}
	
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

//	Configure datasource
	@Bean
	@SuppressWarnings("rawtypes")
	public DataSource getDataSource() {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName(datasourceProperties.getDriver());
		dataSourceBuilder.url(datasourceProperties.getUrl());
		dataSourceBuilder.username(encryptUtil().decryptStringEncoded(datasourceProperties.getUsername()));
		dataSourceBuilder.password(encryptUtil().decryptStringEncoded(datasourceProperties.getPassword()));

		return dataSourceBuilder.build();
	}
	
}