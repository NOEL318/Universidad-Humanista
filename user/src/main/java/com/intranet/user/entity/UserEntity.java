package com.intranet.user.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class UserEntity {

	public UserEntity() {

	}

	@Id
	@GeneratedValue
	@Column(updatable = false, nullable = false)
	private UUID id;


	private String name;
	private String father_surname;
	private String mother_surname;
	private String born_date;
	private String phone;
	private String sex;

	private String address;
	private String photo_url;
	private String role;
	private String curp;
	private String status;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime registry_date;

	// Security
	@Email
	private String email;
	private String password;

	private String access_card_code;

}
