package com.reloadly.devops.utilities;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties("app.datasource")
public class DatasourceProperties {
	private String url;
	private String driver;
	private String username;
	private String password;
}