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
		UserModel service_response = userService.createUserToDataBase(user);
		return generateResponse("User created successfully", service_response);
	}

	@GetMapping("/find_user")
	public ResponseEntity<Map<String, Object>> getUsersList() {
		List<UserModel> results = userService.getUsersFromDatabase();
		return generateResponse("User Finded successfully", results);
	}
}
