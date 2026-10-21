package com.intranet.academic.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;

public class Response {
	public static ResponseEntity<Map<String, Object>> ok(String message, Object object) {
		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("data", object);
		return ResponseEntity.ok(response);
	}
}
