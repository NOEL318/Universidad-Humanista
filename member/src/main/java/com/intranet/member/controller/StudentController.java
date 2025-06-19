package com.intranet.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intranet.member.model.FullStudentModel;
import com.intranet.member.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {
	@Autowired
	private StudentService studentService;

	private ResponseEntity<Map<String, Object>> generateResponse(String message, Object object) {
		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("data", object);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/hi")
	public String testUserModule() {
		return "Hola Member";
	}

	// Get all students
	@GetMapping
	public ResponseEntity<Map<String, Object>> findAllUsers() {
		List<FullStudentModel> results = studentService.findAllUsers();
		return generateResponse("User Finded successfully", results);
	}

	// Get user by id
	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> findUser(@PathVariable String id) {
		System.out.println(id + "myid");
		FullStudentModel results = studentService.findUser(id);
		return generateResponse("User Finded successfully", results);
	}
}
