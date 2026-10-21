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
import com.intranet.academic.entity.SubjectEntity;
import com.intranet.academic.repository.SubjectRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/subject")
public class SubjectController {

	private final SubjectRepository repository;

	SubjectController(SubjectRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	public ResponseEntity<Map<String, Object>> findAll() {
		return Response.ok("Subject Finded successfully", repository.findAll());
	}

	@GetMapping("/group/{groupId}")
	public ResponseEntity<Map<String, Object>> findByGroup(@PathVariable UUID groupId) {
		return Response.ok("Subject Finded successfully", repository.findByGroup(groupId));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> find(@PathVariable UUID id) {
		SubjectEntity entity = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Subject Not Found"));
		return Response.ok("Subject Finded successfully", entity);
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> create(@RequestBody @Valid SubjectEntity entity) {
		entity.setId(null);
		return Response.ok("Subject Created Successfully", repository.save(entity));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> update(@PathVariable UUID id, @RequestBody @Valid SubjectEntity entity) {
		repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Subject Not Found"));
		entity.setId(id);
		return Response.ok("Subject Updated Successfully", repository.save(entity));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable UUID id) {
		repository.deleteById(id);
		return Response.ok("Subject Deleted Successfully", id);
	}
}
