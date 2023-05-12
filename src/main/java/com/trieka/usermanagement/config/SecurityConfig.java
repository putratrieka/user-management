package com.trieka.usermanagement.config;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import com.trieka.usermanagement.security.TokenProvider;
import com.trieka.usermanagement.security.impl.JwtTokenProvider;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	private JwtProperties properties;

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
				.authorizeHttpRequests((authorize) -> {
					try {
						authorize.requestMatchers("/secure/auth/**").permitAll()
								.requestMatchers("/swagger-resources/**").permitAll()
								.requestMatchers("/swagger-ui.html").permitAll()
								.requestMatchers("secure/**").authenticated()
								.anyRequest().permitAll()
									.and()
								.exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
									.and()
								.apply(new AuthTokenConfigure(tokenProvider()));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
		return http.build();
	}
	
	@Bean
	public TokenProvider tokenProvider() throws Exception {
		String jwtSecret = properties.getSecretKey();
		String secretKey = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
				
		JwtTokenProvider provider = new JwtTokenProvider();
		provider.setSecretKey(secretKey);
		provider.setJwtValidityInMinutes(60);
		return provider;
	}

}
