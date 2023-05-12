package com.trieka.usermanagement.dto.response;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DefaultErrorMessage {
	
	@JsonProperty("status")
	private int status;
	
	@JsonProperty("error_message")
	private String message;
	
	@JsonProperty("error_detail")
	private Map<String, String> detail = new HashMap<>();
	
	@JsonProperty("timestamp")
	private Date timestamp;

}
