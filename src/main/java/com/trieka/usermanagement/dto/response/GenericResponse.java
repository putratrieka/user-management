package com.trieka.usermanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenericResponse<T> {
	
	private int code;
	
	
	
	private T data;

}
