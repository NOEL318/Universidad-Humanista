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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intranet.academic.config.Response;
import com.intranet.academic.entity.GradeEntity;
import com.intranet.academic.repository.GradeRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/grade")
public class GradeController {

	private final GradeRepository repository;

	GradeController(GradeRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	public ResponseEntity<Map<String, Object>> findAll() {
		return Response.ok("Grade Finded successfully", repository.findAll());
	}

	@GetMapping("/student/{userId}")
	public ResponseEntity<Map<String, Object>> findByStudent(@PathVariable UUID userId) {
		return Response.ok("Grade Finded successfully", repository.findByStudent(userId));
	}

	// Calificaciones del alumno que inicio sesion
	@GetMapping("/me")
	public ResponseEntity<Map<String, Object>> findMe(@RequestHeader("X-User-Id") UUID userId) {
		return findByStudent(userId);
	}

	@GetMapping("/subject/{subjectId}")
	public ResponseEntity<Map<String, Object>> findBySubject(@PathVariable UUID subjectId) {
		return Response.ok("Grade Finded successfully", repository.findBySubject(subjectId));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> find(@PathVariable UUID id) {
		GradeEntity entity = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Grade Not Found"));
		return Response.ok("Grade Finded successfully", entity);
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> create(@RequestBody @Valid GradeEntity entity) {
		entity.setId(null);
		return Response.ok("Grade Created Successfully", repository.save(entity));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> update(@PathVariable UUID id, @RequestBody @Valid GradeEntity entity) {
		repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Grade Not Found"));
		entity.setId(id);
		return Response.ok("Grade Updated Successfully", repository.save(entity));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable UUID id) {
		repository.deleteById(id);
		return Response.ok("Grade Deleted Successfully", id);
	}
}
