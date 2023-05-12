package com.trieka.usermanagement.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trieka.usermanagement.dto.request.UserLoginRequestDTO;
import com.trieka.usermanagement.dto.request.UserRegisterDTO;
import com.trieka.usermanagement.dto.request.UserUpdateDTO;
import com.trieka.usermanagement.dto.response.AuthResponseDTO;
import com.trieka.usermanagement.dto.response.GenericResponse;
import com.trieka.usermanagement.entity.User;
import com.trieka.usermanagement.mapper.UserMapper;
import com.trieka.usermanagement.security.TokenProvider;
import com.trieka.usermanagement.service.IUserService;

import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("secure")
@Api(tags = "User") 
public class UserController extends BaseController{
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired 
	private TokenProvider tokenProvider;
	
	@PostMapping(value = "/auth/register")
	public GenericResponse<String> register(@Valid @RequestBody UserRegisterDTO userDTO) {
		User user = userService.register(UserMapper.dtoToEntity(userDTO));
		return constructSuccessResponse("SUCCESS");
	}
	
	@PostMapping(value = "/auth/login")
	public GenericResponse<AuthResponseDTO> login(@RequestBody UserLoginRequestDTO authRequest) {
		
		Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getPhone(), authRequest.getPassword()));
		
		String token = tokenProvider.createToken(authRequest.getPhone(), new ArrayList<>());
		User user = getUser(auth);
		
		return constructSuccessResponse(UserMapper.entityToAuthDTO(user, token));
	}

	public String updateUser() {
		return null;
	}
	
	@GetMapping(value = "user/getname")
	public GenericResponse<String> getName() {
		return constructSuccessResponse(userService.getName());
	}
	
	@GetMapping(value = "user/update")
	public GenericResponse<String> update(@RequestBody UserUpdateDTO request) {
		userService.updateUser(request.getName());
		return constructSuccessResponse("SUCCESS");
	}
	
	
	private User getUser(Authentication auth) {
		Object user = auth.getPrincipal();
		if (user instanceof User) {
			return (User) user;
		}
		return null;
	}
	
	public <T> GenericResponse<T> constructSuccessResponse(T data){
		GenericResponse<T> response = new GenericResponse<>();
		response.setCode(200);
		response.setData(data);
		return response;
	}
}
