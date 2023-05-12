package com.trieka.usermanagement.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface IUserSecurityService {
	
	public Authentication login(String phone, String password);

	public UserDetails findUserDetailsByUsername(String username);

}
