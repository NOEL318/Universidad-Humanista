package com.intranet.user.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserModel {

	public UserModel() {

	}

	private UUID id;

	@NotBlank(message = "Nombre es un campo obligatorio.")
	private String name;

	@NotBlank(message = "Apellido paterno es un campo obligatorio.")
	private String father_surname;

	@NotBlank(message = "Apellido materno es un campo obligatorio.")
	private String mother_surname;

	@NotBlank(message = "Fecha de nacimiento es un campo obligatorio.")
	private String born_date;

	@NotBlank(message = "Número telefónico es un campo obligatorio.")
	private String phone;

	@NotBlank(message = "Sexo es un campo obligatorio.")
	private String sex;

	@NotBlank(message = "Dirección es un campo obligatorio.")

	private String address;

	@NotBlank(message = "La fotografía es un campo obligatorio.")
	private String photo_url;

	@NotBlank(message = "El rol es un campo obligatorio.")
	private String role;

	@NotBlank(message = "CURP es un campo obligatorio.")
	private String curp;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime registry_date;

	// Security

	@NotBlank(message = "Email es un campo obligatorio.")
	@Email
	private String email;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotBlank(message = "Contraseña es un campo obligatorio.")
	private String password;

	private String access_card_code;

}
