package com.intranet.user.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.intranet.user.models.UserModel;
import com.intranet.user.services.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	UserController(UserService userService) {
		this.userService = userService;
	}

	private ResponseEntity<Map<String, Object>> generateResponse(String message, Object object) {
		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("data", object);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/hi")
	public String testUserModule() {
		return "Hola Usuario";
	}

	@PostMapping("/create")
	public ResponseEntity<Map<String, Object>> createUser(@RequestBody UserModel user) {
		Map<String, Object> response = new HashMap<>();
		UserModel service_response = userService.createUserToDataBase(user);
		response.put("message", "User created successfully");
		response.put("data", service_response);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/users_list")
	public ResponseEntity<Map<String, Object>> getUsersList(@RequestParam String param) {
		Map<String, Object> response = new HashMap<>();
		List<UserModel> results = userService.getUsersFromDatabase();
		response.put("message", "User created successfully");
		response.put("data", results);
		return ResponseEntity.ok(response);
	}

}
