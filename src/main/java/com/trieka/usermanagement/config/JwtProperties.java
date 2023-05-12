package com.trieka.usermanagement.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "jwt")
@Configuration
public class JwtProperties {
	
	private String secretKey;

}
