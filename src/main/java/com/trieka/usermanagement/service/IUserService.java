package com.trieka.usermanagement.service;

import java.util.Optional;

import com.trieka.usermanagement.entity.User;

public interface IUserService extends GeneralService {
	
	public User register(User user);
	
	public Optional<User> getUser(String userId);
	
	public User updateUser (String string);
	
	public String getName();

}
