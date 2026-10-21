package com.intranet.api_gateway.filter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

// Todas las peticiones se validan con auth antes de pasar
@Component
public class AuthFilter implements GlobalFilter, Ordered {

	private static final Duration CACHE_TTL = Duration.ofSeconds(30);

	// Lo unico que puede ver un alumno (solo GET)
	private static final List<String> STUDENT_PATHS = List.of(
			"/auth/me", "/student/me", "/medical/me", "/grade/me", "/enrollment/me",
			"/task/group/", "/subject", "/group");

	private final ReactiveStringRedisTemplate redis;
	private final WebClient webClient;
	private final ObjectMapper mapper = new ObjectMapper();

	AuthFilter(ReactiveStringRedisTemplate redis, @Value("${services.auth}") String authUrl) {
		this.redis = redis;
		this.webClient = WebClient.builder().baseUrl(authUrl).build();
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest().mutate()
				.headers(h -> {
					h.remove("X-User-Id");
					h.remove("X-User-Role");
				})
				.build();
		String path = request.getPath().value();

		if (path.equals("/auth/login")) {
			return chain.filter(exchange.mutate().request(request).build());
		}

		String token = token(request);
		if (token == null) {
			return reject(exchange, HttpStatus.UNAUTHORIZED);
		}
		String key = "gateway:" + sha256(token);

		if (path.equals("/auth/logout")) {
			return redis.delete(key).then(chain.filter(exchange.mutate().request(request).build()));
		}

		return redis.opsForValue().get(key)
				.switchIfEmpty(validate(token)
						.flatMap(json -> redis.opsForValue().set(key, json, CACHE_TTL).thenReturn(json)))
				.flatMap(json -> {
					Map<String, Object> session = read(json);
					String id = String.valueOf(session.get("id"));
					String role = String.valueOf(session.get("role"));
					if (!allowed(role, request.getMethod(), path)) {
						return reject(exchange, HttpStatus.FORBIDDEN);
					}
					ServerHttpRequest authorized = request.mutate()
							.header("X-User-Id", id)
							.header("X-User-Role", role)
							.build();
					return chain.filter(exchange.mutate().request(authorized).build());
				})
				.switchIfEmpty(Mono.defer(() -> reject(exchange, HttpStatus.UNAUTHORIZED)));
	}

	private Mono<String> validate(String token) {
		return webClient.get().uri("/auth/validate")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				.exchangeToMono(response -> response.statusCode().is2xxSuccessful()
						? response.bodyToMono(Map.class).map(body -> write(body.get("data")))
						: Mono.empty());
	}

	// admin todo, maestro todo menos usuarios y maestros, alumno solo lectura de lo suyo
	private boolean allowed(String role, HttpMethod method, String path) {
		if (role.equals("admin")) {
			return true;
		}
		if (role.equals("teacher")) {
			if (path.startsWith("/user")) {
				return false;
			}
			return !path.startsWith("/teacher") || method == HttpMethod.GET;
		}
		if (role.equals("student")) {
			return method == HttpMethod.GET && STUDENT_PATHS.stream()
					.anyMatch(p -> p.endsWith("/") ? path.startsWith(p) : path.equals(p));
		}
		return false;
	}

	private String token(ServerHttpRequest request) {
		String header = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		if (header != null && header.startsWith("Bearer ")) {
			return header.substring(7);
		}
		HttpCookie cookie = request.getCookies().getFirst("token");
		return cookie == null || cookie.getValue().isBlank() ? null : cookie.getValue();
	}

	private Mono<Void> reject(ServerWebExchange exchange, HttpStatus status) {
		exchange.getResponse().setStatusCode(status);
		return exchange.getResponse().setComplete();
	}

	private String sha256(String value) {
		try {
			byte[] digest = MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8));
			return HexFormat.of().formatHex(digest);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		}
	}

	private Map<String, Object> read(String json) {
		try {
			return mapper.readValue(json, Map.class);
		} catch (Exception e) {
			return Map.of();
		}
	}

	private String write(Object value) {
		try {
			return mapper.writeValueAsString(value);
		} catch (Exception e) {
			return "{}";
		}
	}

	@Override
	public int getOrder() {
		return -1;
	}
}
