package com.projects.SalesSystem.controllers.exceptions;



import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.projects.SalesSystem.services.exceptions.AuthorizationException;
import com.projects.SalesSystem.services.exceptions.DataIntegrity;
import com.projects.SalesSystem.services.exceptions.EmailException;
import com.projects.SalesSystem.services.exceptions.ObjectNotFound;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(ObjectNotFound.class)
	 public ResponseEntity<StandardError> objectNotFound(ObjectNotFound e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), 
				status.getReasonPhrase(), e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	 }
	
	@ExceptionHandler(DataIntegrity.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrity e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), 
				status.getReasonPhrase(), e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		ValidationError err = new ValidationError(System.currentTimeMillis(), status.value(),
				status.getReasonPhrase(), "Validation error", request.getRequestURI());
		
		for(FieldError f: e.getBindingResult().getFieldErrors()) {
			err.getErrors().add(new FieldMessage(f.getField(), f.getDefaultMessage()));
		}
		
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<StandardError> accessDenied(AccessDeniedException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.FORBIDDEN;
		ValidationError err = new ValidationError(System.currentTimeMillis(), status.value(), status.getReasonPhrase(), e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorization(AuthorizationException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.FORBIDDEN;
		ValidationError err = new ValidationError(System.currentTimeMillis(), status.value(), status.getReasonPhrase(), e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(EmailException.class)
	public ResponseEntity<StandardError> email(EmailException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ValidationError err = new ValidationError(System.currentTimeMillis(), status.value(), status.getReasonPhrase(), e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
}
