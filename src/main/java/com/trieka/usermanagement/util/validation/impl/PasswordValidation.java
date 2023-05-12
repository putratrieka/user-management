package com.trieka.usermanagement.util.validation.impl;

import java.util.regex.Pattern;

import com.trieka.usermanagement.util.validation.ValidPassword;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidation implements ConstraintValidator<ValidPassword, String>{
	
private static String PASS_REGEX_UPPER_CASE = ".*[A-Z]";
private static String PASS_REGEX_NUMERICAL = ".*\\d";
	

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		Pattern capitalLeter = Pattern.compile(PASS_REGEX_UPPER_CASE);
		if (!capitalLeter.matcher(value).find()){
			System.out.println(value);
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Invalid password: password must contain at least 1 Capital letter").addConstraintViolation();
			return false;
		}
		
		
		if (!value.matches(PASS_REGEX_NUMERICAL)){
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Invalid password: password must contain at least 1 Numerical letter").addConstraintViolation();
			return false;
		}
		
		return true;

	}
	


}
