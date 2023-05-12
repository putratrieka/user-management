package com.trieka.usermanagement.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "datasource")
@Configuration
public class DataSourceProperties {
	
	private String username;
	private String password;
	private String url;
	private String driverClassName;
	
}
