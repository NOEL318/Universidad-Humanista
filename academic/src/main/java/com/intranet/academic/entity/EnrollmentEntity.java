package com.intranet.academic.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// Inscripcion de un alumno a una materia
@Data
@Entity
@Table(name = "enrollment", uniqueConstraints = @UniqueConstraint(columnNames = { "student_user_id", "subject_id", "period" }))
public class EnrollmentEntity {
	@Id
	@GeneratedValue
	@Column(updatable = false, nullable = false)
	private UUID id;
	@NotNull(message = "El alumno es un campo obligatorio")
	private UUID student_user_id;
	@NotNull(message = "La materia es un campo obligatorio")
	private UUID subject_id;
	private String period;
	private String status; // active, dropped, finished
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime enrollment_date;
}
