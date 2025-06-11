package com.universidadhumanista.Profesores.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

// import com.netflix.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/profesor")
public class ProfesorController {

	private DiscoveryClient discoveryClient;
	private RestClient restClient;

	public ProfesorController(DiscoveryClient discoveryClient, RestClient.Builder restClientBuilder) {
		this.discoveryClient = discoveryClient;
		this.restClient = restClientBuilder.build();
	}

	@GetMapping("/saludoProfesor")
	public String helloWorld() {
		ServiceInstance serviceInstance = discoveryClient.getInstances("Alumnos").get(0);
		String serviceAlumnosResponse = restClient.get().uri(serviceInstance.getUri() + "/estudiante/saludo").retrieve()
				.body(String.class);
		return serviceAlumnosResponse;
	}

}
