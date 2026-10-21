package com.intranet.auth.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intranet.auth.service.AuthService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;

	@Value("${auth.cookie-secure}")
	private boolean cookieSecure;

	AuthController(AuthService authService) {
		this.authService = authService;
	}

	public record LoginRequest(@NotBlank @Email String email, @NotBlank String password) {
	}

	private ResponseEntity<Map<String, Object>> generateResponse(HttpStatus status, String message, Object object) {
		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("data", object);
		return ResponseEntity.status(status).body(response);
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody @Valid LoginRequest request) {
		String token = authService.login(request.email(), request.password());
		ResponseCookie cookie = cookie(token, authService.sessionSeconds());
		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body(Map.of("message", "Login successfully", "data", authService.session(token).orElseThrow()));
	}

	// Lo usa el api-gateway en cada peticion
	@GetMapping({ "/validate", "/me" })
	public ResponseEntity<Map<String, Object>> validate(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String header,
			@CookieValue(value = "token", required = false) String cookie) {
		return authService.session(token(header, cookie))
				.map(session -> generateResponse(HttpStatus.OK, "Valid session", session))
				.orElseGet(() -> generateResponse(HttpStatus.UNAUTHORIZED, "Invalid session", null));
	}

	@PostMapping("/logout")
	public ResponseEntity<Map<String, Object>> logout(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String header,
			@CookieValue(value = "token", required = false) String cookie) {
		authService.logout(token(header, cookie));
		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, cookie("", 0).toString())
				.body(Map.of("message", "Logout successfully"));
	}

	private String token(String header, String cookie) {
		if (header != null && header.startsWith("Bearer ")) {
			return header.substring(7);
		}
		return cookie;
	}

	private ResponseCookie cookie(String value, long maxAge) {
		return ResponseCookie.from("token", value)
				.httpOnly(true)
				.secure(cookieSecure)
				.sameSite("Strict")
				.path("/")
				.maxAge(maxAge)
				.build();
	}
}
