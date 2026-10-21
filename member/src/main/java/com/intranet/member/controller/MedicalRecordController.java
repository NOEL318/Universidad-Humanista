package com.intranet.member.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intranet.member.entity.MedicalRecordEntity;
import com.intranet.member.repository.MedicalRecordRepository;

@RestController
@RequestMapping("/medical")
public class MedicalRecordController {
	@Autowired
	private MedicalRecordRepository medicalRecordRepository;

	private ResponseEntity<Map<String, Object>> generateResponse(String message, Object object) {
		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("data", object);
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<Map<String, Object>> findAll() {
		return generateResponse("Records Finded successfully", medicalRecordRepository.findAll());
	}

	@GetMapping("/me")
	public ResponseEntity<Map<String, Object>> findMe(@RequestHeader("X-User-Id") UUID userId) {
		return findByUser(userId);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<Map<String, Object>> findByUser(@PathVariable UUID userId) {
		MedicalRecordEntity record = medicalRecordRepository.findByUserId(userId).orElse(null);
		return generateResponse("Record Finded successfully", record);
	}

	// Crea o actualiza el expediente del usuario
	@PutMapping("/user/{userId}")
	public ResponseEntity<Map<String, Object>> save(@PathVariable UUID userId, @RequestBody MedicalRecordEntity record) {
		medicalRecordRepository.findByUserId(userId).ifPresentOrElse(r -> record.setId(r.getId()), () -> record.setId(null));
		record.setUser_id(userId);
		return generateResponse("Record Saved successfully", medicalRecordRepository.save(record));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable UUID id) {
		medicalRecordRepository.deleteById(id);
		return generateResponse("Record Deleted successfully", id);
	}
}
