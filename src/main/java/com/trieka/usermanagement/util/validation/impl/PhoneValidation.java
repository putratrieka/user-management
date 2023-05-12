package com.trieka.usermanagement.util.validation.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.trieka.usermanagement.service.impl.UserService;
import com.trieka.usermanagement.util.validation.ValidPhoneNumber;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidation implements ConstraintValidator<ValidPhoneNumber, String>{
	
	@Autowired
	UserService userService;
	
	private PhoneValidatorType TYPE;
	
	@Override
	public void initialize(ValidPhoneNumber constraintAnnotation) {
		this.TYPE = constraintAnnotation.type() == null ? PhoneValidatorType.REGISTER : constraintAnnotation.type() ;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		switch (TYPE) {
		case REGISTER: {
			return validatePhoneNumberOnRegistration(value, context);
		}
		case UPDATE:{
			return validatePhoneNumberOnUpdate(value);
		}
		default:
			return false;
		}

	}
	
	private boolean validatePhoneNumberOnRegistration(String value, ConstraintValidatorContext context) {
		if (userService.getUser(value).isPresent()) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Phone number already registered").addConstraintViolation();
			return false;
		}
		
		return value.startsWith("08");
	}

	private boolean validatePhoneNumberOnUpdate(String value) {
		return true;
	}

	public enum PhoneValidatorType{
		REGISTER,
		UPDATE
	}

}
