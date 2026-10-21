package com.intranet.member.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intranet.member.model.TeacherModel;
import com.intranet.member.service.TeacherService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
	@Autowired
	private TeacherService teacherService;

	private ResponseEntity<Map<String, Object>> generateResponse(String message, Object object) {
		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("data", object);
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<Map<String, Object>> findAll() {
		return generateResponse("Teachers Finded successfully", teacherService.findAll());
	}

	@GetMapping("/me")
	public ResponseEntity<Map<String, Object>> findMe(@RequestHeader("X-User-Id") UUID userId) {
		return generateResponse("Teacher Finded successfully", teacherService.findByUserId(userId));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> find(@PathVariable String id) {
		return generateResponse("Teacher Finded successfully", teacherService.find(id));
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> create(@RequestBody @Valid TeacherModel model) {
		return generateResponse("Teacher Created Successfully", teacherService.create(model));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> update(@PathVariable String id, @RequestBody TeacherModel model) {
		return generateResponse("Teacher Updated Successfully", teacherService.update(id, model));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable String id) {
		return generateResponse("Teacher Deleted Succesfully", teacherService.delete(id));
	}
}
