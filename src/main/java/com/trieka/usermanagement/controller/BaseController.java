package com.trieka.usermanagement.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.trieka.usermanagement.dto.response.DefaultErrorMessage;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

public class BaseController {
	
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler({
			BadCredentialsException.class,
	})
	public DefaultErrorMessage handleAuthenticationFailedException(RuntimeException ex) {
		DefaultErrorMessage errorMessage = new DefaultErrorMessage();
		errorMessage.setStatus(HttpStatus.UNAUTHORIZED.value());
		errorMessage.setMessage(ex.getMessage());
		errorMessage.setTimestamp(new Date());

		return errorMessage;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({
			MethodArgumentNotValidException.class,
			HttpMessageNotReadableException.class,
			HttpMessageConversionException.class,
			MethodArgumentTypeMismatchException.class,
			MissingServletRequestParameterException.class,
			ConstraintViolationException.class,
			BindException.class})
	public DefaultErrorMessage handleValidationExceptions(Exception ex) {
		DefaultErrorMessage errorMessage = new DefaultErrorMessage();
		errorMessage.setStatus(HttpStatus.BAD_REQUEST.value());
		errorMessage.setMessage("Invalid Parameter!");
		errorMessage.setTimestamp(new Date());

		if (ex instanceof MethodArgumentNotValidException) {
			Map<String, String> errors = new HashMap<>();
			((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors().forEach((error) -> {
				String fieldName = ((FieldError) error).getField();
				String validationMessage = error.getDefaultMessage();
				errors.put(fieldName, validationMessage);
			});
			errorMessage.setDetail(errors);
		}
		
		

		if (ex instanceof MethodArgumentTypeMismatchException) {
			String propertyName = ((MethodArgumentTypeMismatchException) ex).getName();
			String invalidValue = ((MethodArgumentTypeMismatchException) ex).getValue().toString();

			Map<String, String> errors = new HashMap<>();
			errors.put(propertyName, "Invalid value: " + invalidValue);
			errorMessage.setDetail(errors);
		}

		
		if (ex instanceof HttpMessageConversionException) {
			HttpMessageConversionException e = ((HttpMessageConversionException) ex);
			errorMessage.setMessage(e.getMostSpecificCause().getMessage());
		}

		if (ex instanceof MissingServletRequestParameterException) {
			MissingServletRequestParameterException e = ((MissingServletRequestParameterException) ex);
			Map<String, String> errors = new HashMap<>();
			errors.put(e.getParameterName(), e.getMessage());
			errorMessage.setDetail(errors);
		}

		if (ex instanceof ConstraintViolationException) {
			ConstraintViolationException e = (ConstraintViolationException) ex;
			
			Map<String, String> errors = new HashMap<>();			
			Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
			for (ConstraintViolation<?> v : violations) {
				String field = StringUtils.substringAfterLast(v.getPropertyPath().toString(), ".");
				errors.put(field, v.getMessage());
			}	
			
			errorMessage.setMessage("validation failed");
			errorMessage.setDetail(errors);
		}
		
		if (ex instanceof BindException) {
			BindException e = (BindException) ex;
			List<ObjectError> objectErrors = e.getAllErrors();
			errorMessage.setMessage("validation failed");
			Map<String, String> errors = new HashMap<>();
			for (ObjectError oe : objectErrors) {
				String field = StringUtils.substringAfterLast(oe.getCodes()[0], ".");
				errors.put(field, oe.getDefaultMessage());
			}
			errorMessage.setDetail(errors);
		}
		return errorMessage;
	}

}
