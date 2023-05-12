package com.trieka.usermanagement.util.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.trieka.usermanagement.util.validation.impl.PhoneValidation;
import com.trieka.usermanagement.util.validation.impl.PhoneValidation.PhoneValidatorType;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = {PhoneValidation.class})
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPhoneNumber {
	
	public abstract String message() default "Invalid phone number.";
	
	public abstract Class<?>[] groups() default {};

	public abstract Class<? extends Payload>[] payload() default {}; 
	
	public abstract PhoneValidatorType type() default PhoneValidatorType.REGISTER;
}
