package com.intranet.auth.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Base64;
import java.util.HexFormat;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intranet.auth.entity.AccountEntity;
import com.intranet.auth.repository.AccountRepository;

@Service
public class AuthService {

	private final AccountRepository accountRepository;
	private final StringRedisTemplate redis;
	private final ObjectMapper mapper = new ObjectMapper();
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
	private final SecureRandom random = new SecureRandom();
	// Para que tarde lo mismo aunque el correo no exista
	private final String dummyHash = passwordEncoder.encode("dummy-password");

	@Value("${auth.session-hours}")
	private long sessionHours;
	@Value("${auth.max-attempts}")
	private long maxAttempts;
	@Value("${auth.lock-minutes}")
	private long lockMinutes;

	AuthService(AccountRepository accountRepository, StringRedisTemplate redis) {
		this.accountRepository = accountRepository;
		this.redis = redis;
	}

	public String login(String email, String password) {
		String mail = email.trim().toLowerCase();
		String attemptsKey = "login:attempts:" + mail;
		String attempts = redis.opsForValue().get(attemptsKey);
		if (attempts != null && Long.parseLong(attempts) >= maxAttempts) {
			throw new LockedException("Demasiados intentos, intenta en " + lockMinutes + " minutos");
		}

		Optional<AccountEntity> account = accountRepository.findByEmail(mail);
		String hash = account.map(AccountEntity::getPassword).orElse(dummyHash);
		if (!passwordEncoder.matches(password, hash) || account.isEmpty()) {
			redis.opsForValue().increment(attemptsKey);
			redis.expire(attemptsKey, Duration.ofMinutes(lockMinutes));
			throw new BadCredentialsException("Correo o contraseña incorrectos");
		}
		redis.delete(attemptsKey);

		byte[] bytes = new byte[32];
		random.nextBytes(bytes);
		String token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
		redis.opsForValue().set(sessionKey(token), toJson(account.get()), Duration.ofHours(sessionHours));
		return token;
	}

	public Optional<Map<String, Object>> session(String token) {
		if (token == null || token.isBlank()) {
			return Optional.empty();
		}
		String json = redis.opsForValue().get(sessionKey(token));
		if (json == null) {
			return Optional.empty();
		}
		try {
			return Optional.of(mapper.readValue(json, Map.class));
		} catch (JsonProcessingException e) {
			return Optional.empty();
		}
	}

	public void logout(String token) {
		if (token != null) {
			redis.delete(sessionKey(token));
		}
	}

	public long sessionSeconds() {
		return Duration.ofHours(sessionHours).toSeconds();
	}

	// En redis solo se guarda el hash del token
	private String sessionKey(String token) {
		try {
			byte[] digest = MessageDigest.getInstance("SHA-256").digest(token.getBytes(StandardCharsets.UTF_8));
			return "session:" + HexFormat.of().formatHex(digest);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		}
	}

	private String toJson(AccountEntity account) {
		try {
			return mapper.writeValueAsString(Map.of(
					"id", account.getId().toString(),
					"email", account.getEmail(),
					"role", account.getRole(),
					"name", account.getName() + " " + Optional.ofNullable(account.getFather_surname()).orElse("")));
		} catch (JsonProcessingException e) {
			throw new IllegalStateException(e);
		}
	}

	public static class BadCredentialsException extends RuntimeException {
		public BadCredentialsException(String message) {
			super(message);
		}
	}

	public static class LockedException extends RuntimeException {
		public LockedException(String message) {
			super(message);
		}
	}
}
