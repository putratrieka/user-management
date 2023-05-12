package com.trieka.usermanagement.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.trieka.usermanagement.entity.User;
import com.trieka.usermanagement.repo.UserRepository;
import com.trieka.usermanagement.service.impl.UserService;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	@Mock
	private UserRepository userRepository;
	
	@Mock 
	private PasswordEncoder passwordEncoder;
	
	@InjectMocks
	private UserService userService;


	@Test
	public void shouldSuccessRegister() {
		String encodedPassword = "$2a$10$FXCbYW6ufnmiiPk93OWX5.3zRcaVrkQ0MhXXb4wPVamQe3YWakE8C";
		
		User user = new User();
		user.setName("name");
		user.setPassword("Testing123");
		user.setPhone("08090902332");
		
		
		when(userRepository.findById(anyString())).thenReturn(Optional.empty());
		when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword);
		when(userRepository.save(any(User.class))).thenReturn(user);
		
		User u = userService.register(user);
		
		System.out.println(u);
		assertThat(u).isNotNull();
		assertEquals(user.getPassword(), encodedPassword);
		verify(userRepository).save(any(User.class));

	}
	
	@Test
	public void shouldSuccessLogin() {
		
		String phone = "08090902332";
		String password = "Testing123";
		String encodedPassword = "$2a$10$FXCbYW6ufnmiiPk93OWX5.3zRcaVrkQ0MhXXb4wPVamQe3YWakE8C";
		
		User user = new User();
		user.setName("name");
		user.setPassword(encodedPassword);
		user.setPhone(phone);
		
		
		when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
		when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
		
		Authentication auth = userService.login(phone, password);
		
		assertThat(auth).isNotNull();
		assertEquals(auth.getPrincipal(), user);

	}
	
	@Test
	public void shouldGetName() {

		User user = new User();
		user.setName("name");
		user.setPassword("$2a$10$FXCbYW6ufnmiiPk93OWX5.3zRcaVrkQ0MhXXb4wPVamQe3YWakE8C");
		user.setPhone("08090902332");

		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(securityContext.getAuthentication().getPrincipal()).thenReturn(user);
		SecurityContextHolder.setContext(securityContext);		
		
		String name = userService.getName();
		
		assertEquals(name, user.getName());
		
	}
	
	@Test
	public void shouldUpdateName() {
		String updatedName = "Name Updated";
		
		User user = new User();
		user.setName("name");
		user.setPassword("$2a$10$FXCbYW6ufnmiiPk93OWX5.3zRcaVrkQ0MhXXb4wPVamQe3YWakE8C");
		user.setPhone("08090902332");
		
		User updatedUser = user;
		updatedUser.setName(updatedName);

		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(securityContext.getAuthentication().getPrincipal()).thenReturn(user);
		SecurityContextHolder.setContext(securityContext);		
		
		when(userRepository.save(user)).thenReturn(updatedUser);
		
		User u = userService.updateUser(updatedName);
		
		assertThat(u).isNotNull();
		assertEquals(u.getName(), updatedName);
	}
}
