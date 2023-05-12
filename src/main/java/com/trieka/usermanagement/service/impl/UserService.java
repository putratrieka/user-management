package com.trieka.usermanagement.service.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trieka.usermanagement.entity.User;
import com.trieka.usermanagement.repo.UserRepository;
import com.trieka.usermanagement.security.TokenProvider;
import com.trieka.usermanagement.service.IUserSecurityService;
import com.trieka.usermanagement.service.IUserService;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService implements IUserService, IUserSecurityService{

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public Optional<User> getUser(String phone) {
		return userRepository.findById(phone);
	}

	@Override
	public User register(User user) {
		Optional<User> existingUser = getExistingUser(user.getPhone());
		System.out.println(existingUser);
		if (existingUser.isPresent()) {
			throw new ValidationException("User already exist");
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		User savedUser =  userRepository.save(user);
		System.out.println(savedUser);
		return savedUser;
	}
	
	@Override
	public Authentication login(String phone, String password) {
		Optional<User> user = userRepository.findById(phone);
		
		if (!user.isPresent()) {
			log.info("user not found");
			throw new BadCredentialsException("Invalid phone or password");
		}
		if (!passwordEncoder.matches(password, user.get().getPassword())) {
			throw new BadCredentialsException("Invalid phone or password");
		}
		return new UsernamePasswordAuthenticationToken(user.get(), null,new ArrayList<>());
	}
	
	@Override
	public String getName() {
		return getCurrentUser().getName();
	}


	@Override
	public User updateUser(String name) {
		User user = getCurrentUser();
		user.setName(name);
		return userRepository.save(user);
	}


	@Override
	public UserDetails findUserDetailsByUsername(String phone) {
		Optional<User> user = getUser(phone);
		if (user.isPresent()) {
			return user.get();
		}
		return null;
	}
	
	private Optional<User> getExistingUser(String phone) {
		return userRepository.findById(phone);
	}


}
