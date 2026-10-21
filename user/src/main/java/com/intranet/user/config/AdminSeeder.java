package com.intranet.user.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.intranet.user.entity.UserEntity;
import com.intranet.user.repository.UserRepository;

// Crea el primer admin si no existe ninguno
@Component
public class AdminSeeder implements CommandLineRunner {

	private final UserRepository userRepository;
	private Logger log = LogManager.getLogger(AdminSeeder.class);

	@Value("${admin.email}")
	private String email;
	@Value("${admin.password}")
	private String password;

	AdminSeeder(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void run(String... args) {
		if (userRepository.existsByRole("admin") || password.isBlank()) {
			return;
		}
		UserEntity admin = new UserEntity();
		admin.setName("Administrador");
		admin.setFather_surname("");
		admin.setMother_surname("");
		admin.setRole("admin");
		admin.setEmail(email.toLowerCase());
		admin.setPassword(new BCryptPasswordEncoder(12).encode(password));
		userRepository.save(admin);
		log.info("Admin creado: {}", email);
	}
}
