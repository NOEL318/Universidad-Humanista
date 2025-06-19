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
	private UUID userId;
	@NotBlank(message = "Matricula es un campo obligatorio")
	private String enrollmentnumber; // matricula
	@NotBlank(message = "Programa Academico es un campo obligatorio")
	private String academicProgram;
	@NotBlank(message = "Semestre es un campo obligatorio")
	private String semester;

	@NotBlank(message = "Estatus es un campo obligatorio")
	private String status; // active, suspended, graduated, off

	@NotNull(message = "El asesor es un campo obligatorio o debe ir vacio")
	private UUID advisorId; // asesor o tutor academico
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime enrollment_date; // fecha de inscripcion

	private LocalDateTime graduationDate; // fecha o estimacion de graduacion

	private UUID groupId;
	@NotBlank(message = "Beca es un campo obligatorio")
	private Boolean scholarhsip; // tiene beca? true \ false
	private UUID scholarshipId; // ID de la tabla de becas en caso de tener beca

	@NotNull(message = "Servicio Social es un campo obligatorio o debe ir vacio")
	private String serviceSocial;
	@NotNull(message = "Practicas Profesionales es un campo obligatorio o debe ir vacio")
	private String internship;

	private String turn;

	private String notes;

	@NotNull(message = "Expediente es un campo obligatorio o debe ir vacio")
	private UUID medicalRecordId;

	private Integer disciplinaryReportCount; 
}
