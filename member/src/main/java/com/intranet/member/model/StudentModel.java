package com.intranet.member.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StudentModel {

	public StudentModel() {

	}

	@Id
	@GeneratedValue
	@Column(updatable = false, nullable = false)
	private UUID id;
	private UUID user_id;
	@NotBlank(message = "Matricula es un campo obligatorio")
	private String enrollment_number; // matricula
	@NotBlank(message = "Programa Academico es un campo obligatorio")
	private String academic_program;
	@NotBlank(message = "Semestre es un campo obligatorio")
	private String semester;

	@NotBlank(message = "Estatus es un campo obligatorio")
	private String status; // active, suspended, graduated, off

	@NotNull(message = "El asesor es un campo obligatorio o debe ir vacio")
	private UUID advisor_id; // asesor o tutor academico
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime enrollment_date; // fecha de inscripcion

	private LocalDateTime graduation_date; // fecha o estimacion de graduacion

	private UUID group_id;
	private Boolean scholarship; // tiene beca? true \ false
	private UUID scholarship_id; // ID de la tabla de becas en caso de tener beca

	@NotNull(message = "Servicio Social es un campo obligatorio o debe ir vacio")
	private String service_social_status;
	@NotNull(message = "Practicas Profesionales es un campo obligatorio o debe ir vacio")
	private String internship_status;

	private String turn;
	private Double score;
	private String notes;
	@NotNull(message = "Expediente es un campo obligatorio o debe ir vacio")
	private UUID medical_record_id;

	private Integer disciplinary_report_count;
}
