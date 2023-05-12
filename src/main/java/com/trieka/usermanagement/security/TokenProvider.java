package com.trieka.usermanagement.security;

import java.util.List;

import org.springframework.security.core.Authentication;

import jakarta.servlet.http.HttpServletRequest;

public interface TokenProvider {

	public String createToken(String username, List<String> roles);

	public Authentication validateToken(String token);

	public String resolveToken(HttpServletRequest req);
	
}
