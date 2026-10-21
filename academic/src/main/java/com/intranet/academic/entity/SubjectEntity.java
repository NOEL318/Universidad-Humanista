package com.intranet.academic.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "subject")
public class SubjectEntity {
	@Id
	@GeneratedValue
	@Column(updatable = false, nullable = false)
	private UUID id;
	@NotBlank(message = "Nombre es un campo obligatorio")
	private String name;
	private String code;
	private Integer credits;
	private String description;
	private UUID teacher_user_id;
	private UUID group_id;
}
