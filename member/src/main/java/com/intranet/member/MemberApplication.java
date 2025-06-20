package com.intranet.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MemberApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemberApplication.class, args);
	}
	// Change Between dev profiles
	// .$: export SPRING_PROFILES_ACTIVE=prod || dev || etc.

}
