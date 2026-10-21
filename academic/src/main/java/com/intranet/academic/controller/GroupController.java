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
import com.intranet.academic.entity.GroupEntity;
import com.intranet.academic.repository.GroupRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/group")
public class GroupController {

	private final GroupRepository repository;

	GroupController(GroupRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	public ResponseEntity<Map<String, Object>> findAll() {
		return Response.ok("Group Finded successfully", repository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> find(@PathVariable UUID id) {
		GroupEntity entity = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Group Not Found"));
		return Response.ok("Group Finded successfully", entity);
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> create(@RequestBody @Valid GroupEntity entity) {
		entity.setId(null);
		return Response.ok("Group Created Successfully", repository.save(entity));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> update(@PathVariable UUID id, @RequestBody @Valid GroupEntity entity) {
		repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Group Not Found"));
		entity.setId(id);
		return Response.ok("Group Updated Successfully", repository.save(entity));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable UUID id) {
		repository.deleteById(id);
		return Response.ok("Group Deleted Successfully", id);
	}
}
