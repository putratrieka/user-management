package com.trieka.usermanagement.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class AuthTokenFilter extends GenericFilterBean{
	
	private TokenProvider tokenProvider;
	
	public AuthTokenFilter(TokenProvider provider) {
		this.tokenProvider = provider;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String token = tokenProvider.resolveToken((HttpServletRequest) request);
		if (token != null) {
			Authentication auth = tokenProvider.validateToken(token);
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		chain.doFilter(request, response);
	}

}
