package com.intranet.academic.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intranet.academic.config.Response;
import com.intranet.academic.entity.TaskEntity;
import com.intranet.academic.repository.TaskRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/task")
public class TaskController {

	private final TaskRepository repository;

	TaskController(TaskRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	public ResponseEntity<Map<String, Object>> findAll() {
		return Response.ok("Task Finded successfully", repository.findAll());
	}

	@GetMapping("/group/{groupId}")
	public ResponseEntity<Map<String, Object>> findByGroup(@PathVariable UUID groupId) {
		return Response.ok("Task Finded successfully", repository.findByGroup(groupId));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> find(@PathVariable UUID id) {
		TaskEntity entity = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task Not Found"));
		return Response.ok("Task Finded successfully", entity);
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> create(@RequestBody @Valid TaskEntity entity) {
		entity.setId(null);
		return Response.ok("Task Created Successfully", repository.save(entity));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> update(@PathVariable UUID id, @RequestBody @Valid TaskEntity entity) {
		repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task Not Found"));
		entity.setId(id);
		return Response.ok("Task Updated Successfully", repository.save(entity));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable UUID id) {
		repository.deleteById(id);
		return Response.ok("Task Deleted Successfully", id);
	}
}
