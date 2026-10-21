package com.intranet.auth.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.intranet.auth.service.AuthService.BadCredentialsException;
import com.intranet.auth.service.AuthService.LockedException;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<Map<String, Object>> handleBadCredentials(BadCredentialsException ex) {
		return response(HttpStatus.UNAUTHORIZED, ex.getMessage());
	}

	@ExceptionHandler(LockedException.class)
	public ResponseEntity<Map<String, Object>> handleLocked(LockedException ex) {
		return response(HttpStatus.TOO_MANY_REQUESTS, ex.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
		return response(HttpStatus.BAD_REQUEST, "Correo o contraseña inválidos");
	}

	// No regresamos detalles del error al cliente
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
		log.error("Error en auth", ex);
		return response(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno");
	}

	private ResponseEntity<Map<String, Object>> response(HttpStatus status, String message) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("status", status.value());
		responseBody.put("message", message);
		return new ResponseEntity<>(responseBody, status);
	}
}
