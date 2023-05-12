package com.trieka.usermanagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException; 
import org.springframework.stereotype.Component;

import com.trieka.usermanagement.service.IUserSecurityService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{
	
	@Autowired
	private IUserSecurityService userSecurityService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String phone = authentication.getName();
		String password = authentication.getCredentials().toString();
		return userSecurityService.login(phone, password);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
