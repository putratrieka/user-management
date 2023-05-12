package com.trieka.usermanagement.dto.response;

import lombok.Data;

@Data
public class AuthResponseDTO {
	
	private String phone;
	
	private String name;
	
	private String token;

}
