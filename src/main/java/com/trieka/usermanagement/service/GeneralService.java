package com.trieka.usermanagement.service;

import org.springframework.security.core.context.SecurityContextHolder;

import com.trieka.usermanagement.entity.User;

public interface GeneralService {
	
	public default User getCurrentUser() {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (auth instanceof User) {
			return (User) auth;
		}
		throw new IllegalStateException("No User Object Available");
	}

}
