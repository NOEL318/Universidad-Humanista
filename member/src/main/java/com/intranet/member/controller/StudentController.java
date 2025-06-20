package com.intranet.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intranet.member.model.FullStudentModel;
import com.intranet.member.model.StudentModel;
import com.intranet.member.service.StudentService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

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

	// Test Route
	@GetMapping("/hi")
	public String testUserModule() {
		return "Hola Member";
	}

	// Get all students
	@GetMapping
	public ResponseEntity<Map<String, Object>> findAllUsers() {
		List<FullStudentModel> results = studentService.findAllUsers();
		return generateResponse("Student Finded successfully", results);
	}

	// Get user by id
	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> findUser(@PathVariable String id) {
		FullStudentModel results = studentService.findUser(id);
		return generateResponse("Student Finded successfully", results);
	}

	// Create Student with userID
	@PostMapping
	public ResponseEntity<Map<String, Object>> newStudent(@RequestBody @Valid StudentModel model) {
		StudentModel results = studentService.createStudentToDatabase(model);
		return generateResponse("Student Created Successfully", results);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> putMethodName(@PathVariable String id, @RequestBody StudentModel entity) {
		StudentModel results = studentService.updateStudentFromDatabase(id, entity);
		return generateResponse("Student Updated Successfully", results);
	}

	// Delete Student
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> deleteStudent(@PathVariable String id) {
		System.out.println(id + "mi id");
		StudentModel results = studentService.deleteStudentFromDatabase(id);
		return generateResponse("Student Deleted Succesfully", results);
	}
}
