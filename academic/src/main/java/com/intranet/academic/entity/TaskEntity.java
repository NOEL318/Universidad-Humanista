package com.intranet.academic.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "task")
public class TaskEntity {
	@Id
	@GeneratedValue
	@Column(updatable = false, nullable = false)
	private UUID id;
	@NotBlank(message = "Titulo es un campo obligatorio")
	private String title;
	@Column(length = 2000)
	private String description;
	@NotNull(message = "La materia es un campo obligatorio")
	private UUID subject_id;
	private UUID group_id;
	private LocalDate due_date;
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime created_at;
}
