package com.intranet.member.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.intranet.member.model.UserModel;

@Service
public class UserWebClientService {
	@Autowired
	private WebClient webClient;

	public UserModel getUserById(UUID id) {
		return webClient.get()
				.uri("/user/{id}", id)
				.retrieve()
				.bodyToMono(UserModel.class)
				.block();

	}

	public List<UserModel> getUsersListById(List<UUID> ids) {
		return webClient.post()
				.uri("/user/allById")
				.bodyValue(ids)
				.retrieve()
				.bodyToFlux(UserModel.class)
				.collectList()
				.block();
	}
}
