package com.intranet.academic.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "grade")
public class GradeEntity {
	@Id
	@GeneratedValue
	@Column(updatable = false, nullable = false)
	private UUID id;
	@NotNull(message = "El alumno es un campo obligatorio")
	private UUID student_user_id;
	@NotNull(message = "La materia es un campo obligatorio")
	private UUID subject_id;
	private String partial; // parcial 1, 2, 3 o final
	@NotNull(message = "La calificacion es un campo obligatorio")
	@DecimalMin(value = "0", message = "La calificacion debe estar entre 0 y 10")
	@DecimalMax(value = "10", message = "La calificacion debe estar entre 0 y 10")
	private Double score;
	private String notes;
	@UpdateTimestamp
	private LocalDateTime updated_at;
}
