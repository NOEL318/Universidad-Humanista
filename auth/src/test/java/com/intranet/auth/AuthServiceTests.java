package com.intranet.auth;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import com.intranet.auth.entity.AccountEntity;
import com.intranet.auth.repository.AccountRepository;
import com.intranet.auth.service.AuthService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AuthServiceTests {

	@Mock
	private AccountRepository accountRepository;
	@Mock
	private StringRedisTemplate redis;
	@Mock
	private ValueOperations<String, String> values;
	@InjectMocks
	private AuthService authService;

	private AccountEntity account;

	@BeforeEach
	void setUp() {
		ReflectionTestUtils.setField(authService, "sessionHours", 8L);
		ReflectionTestUtils.setField(authService, "maxAttempts", 5L);
		ReflectionTestUtils.setField(authService, "lockMinutes", 15L);
		when(redis.opsForValue()).thenReturn(values);
		account = new AccountEntity();
		account.setId(UUID.randomUUID());
		account.setEmail("maestro@intranet.local");
		account.setName("Maestro");
		account.setRole("teacher");
		account.setPassword(new BCryptPasswordEncoder(4).encode("secreta"));
	}

	@Test
	void login_success() {
		when(accountRepository.findByEmail("maestro@intranet.local")).thenReturn(Optional.of(account));
		String token = authService.login(" Maestro@Intranet.local ", "secreta");
		assertNotNull(token);
		verify(values).set(startsWith("session:"), contains("teacher"), any());
	}

	@Test
	void login_wrong_password() {
		when(accountRepository.findByEmail(anyString())).thenReturn(Optional.of(account));
		assertThrows(AuthService.BadCredentialsException.class, () -> authService.login("maestro@intranet.local", "mala"));
		verify(values).increment("login:attempts:maestro@intranet.local");
	}

	@Test
	void login_unknown_email() {
		when(accountRepository.findByEmail(anyString())).thenReturn(Optional.empty());
		assertThrows(AuthService.BadCredentialsException.class, () -> authService.login("nadie@intranet.local", "x"));
	}

	@Test
	void login_locked() {
		when(values.get("login:attempts:maestro@intranet.local")).thenReturn("5");
		assertThrows(AuthService.LockedException.class, () -> authService.login("maestro@intranet.local", "secreta"));
		verify(accountRepository, never()).findByEmail(anyString());
	}
}
