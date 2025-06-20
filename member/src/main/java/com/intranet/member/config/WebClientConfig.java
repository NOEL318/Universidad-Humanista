package com.intranet.member.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

//User webClient pq resttemplate deprecated
@Configuration
@Profile("prod")
public class WebClientConfig {
	@Value("${webclient.base-url}")
	private String baseUrl;

	@Bean
	public WebClient webClient() {
		return WebClient.builder()
				.baseUrl(baseUrl)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}

}
