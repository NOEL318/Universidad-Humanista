package com.intranet.auth.entity;

import java.util.UUID;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

// Solo lectura, la tabla la administra el servicio user
@Data
@Entity
@Immutable
@Table(name = "users")
public class AccountEntity {
	@Id
	private UUID id;
	private String name;
	private String father_surname;
	private String email;
	private String password;
	private String role;
}
