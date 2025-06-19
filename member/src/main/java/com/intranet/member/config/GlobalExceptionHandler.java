package com.intranet.member.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	// Handler para validaciones fallidas
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationExceptions(
			MethodArgumentNotValidException ex) {

		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("status", HttpStatus.BAD_REQUEST.value());
		responseBody.put("message", "Validation failed");
		responseBody.put("errors", errors);

		return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
	}

	// Handler general para cualquier otra excepción no capturada
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		responseBody.put("message", ex.getMessage());
		responseBody.put("error", ex.toString());

		return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}