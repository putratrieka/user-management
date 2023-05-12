package com.trieka.usermanagement.dto.request;

import lombok.Data;

@Data
public class UserLoginRequestDTO {
	
	private String phone;
	
	private String password;

}
