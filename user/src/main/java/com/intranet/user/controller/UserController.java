package com.intranet.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intranet.user.model.UserModel;
import com.intranet.user.service.UserService;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

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

	// createUser
	@PostMapping
	public ResponseEntity<Map<String, Object>> createUser(@RequestBody @Valid UserModel user) {
		UserModel service_response = userService.createUserToDataBase(user);
		return generateResponse("User created successfully", service_response);
	}

	// getUsersList
	@GetMapping("/all")
	public ResponseEntity<Map<String, Object>> getUsersList() {
		List<UserModel> results = userService.getUsersFromDatabase();
		return generateResponse("User Finded successfully", results);
	}

	// getUsersListById
	@PostMapping("/allById")
	public List<UserModel> getUsersList(@RequestBody List<UUID> ids) {
		List<UserModel> results = userService.getUsersFromDatabaseByIds(ids);
		return results;
	}

	// getUser
	@GetMapping("/{id}")
	public UserModel getUser(@PathVariable("id") String id) {
		System.out.println("Callign to id: " + id);
		UserModel results = userService.getUserFromDatabase(id);
		return results;
	}

	// Edit user
	@PutMapping
	public ResponseEntity<Map<String, Object>> editUser(@RequestBody UserModel user) {
		UserModel results = userService.editUserInDatabase(user);
		return generateResponse("User Modified successfully", results);
	}

	// Delete user
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable("id") String id) {
		UserModel results = userService.deleteUserFromDatabase(id);
		return generateResponse("User Deleted successfully", results);
	}
}
