package com.trieka.usermanagement.mapper;

import com.trieka.usermanagement.dto.request.UserRegisterDTO;
import com.trieka.usermanagement.dto.response.AuthResponseDTO;
import com.trieka.usermanagement.entity.User;

public class UserMapper {
	
	public static User dtoToEntity(UserRegisterDTO dto) {
		User user = new User();
		user.setPhone(dto.getPhone());
		user.setPassword(dto.getPassword());
		user.setName(dto.getName());
		
		return user;
	}
	
	public static AuthResponseDTO entityToAuthDTO(User u, String token) {
		AuthResponseDTO dto = new AuthResponseDTO();
		dto.setName(u.getName());
		dto.setPhone(u.getPhone());
		dto.setToken(token);
		return dto;
	}

}
