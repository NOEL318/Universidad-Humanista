package com.intranet.member.client;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.intranet.member.model.UserModel;

@FeignClient(name = "user", url = "http://localhost:8081", fallback = StudentClientFallback.class)
public interface StudentClient {
	@GetMapping("/user/{id}")
	UserModel getUserById(@PathVariable UUID id);

	@PostMapping("/user/allById")
	List<UserModel> getUsersListById(@RequestBody List<UUID> id);


}
