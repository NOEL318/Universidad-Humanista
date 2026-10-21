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
@Table(name = "school_group")
public class GroupEntity {
	@Id
	@GeneratedValue
	@Column(updatable = false, nullable = false)
	private UUID id;
	@NotBlank(message = "Nombre es un campo obligatorio")
	private String name;
	private String semester;
	private String turn;
	private String school_year; // ciclo escolar 2025-2026
	private UUID advisor_user_id; // maestro tutor del grupo
}
