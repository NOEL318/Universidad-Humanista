package com.intranet.member.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "student")
public class StudentEntity {
	public StudentEntity() {

	}

	@Id
	@GeneratedValue
	@Column(updatable = false, nullable = false)
	private UUID id;

	@Column(name = "user_id")
	private UUID userId;

	@Column(name = "enrollment_number")
	private String enrollmentnumber; // matrícula

	@Column(name = "academic_program")
	private String academicProgram;

	private String semester;

	private String status; // active, suspended, graduated, off

	@Column(name = "advisor_id")
	private UUID advisorId; // asesor o tutor académico

	@Column(name = "enrollment_date", updatable = false)
	private LocalDateTime enrollment_date; // fecha de inscripción

	@Column(name = "graduation_date")
	private LocalDateTime graduationDate; // fecha o estimación de graduación

	@Column(name = "group_id")
	private UUID groupId;

	private Boolean scholarhsip; // tiene beca? true/false

	@Column(name = "scholarship_id")
	private UUID scholarshipId; // ID de la tabla becas en caso de tener beca

	@Column(name = "service_social_status")
	private String serviceSocial;

	@Column(name = "internship_status")
	private String internship;

	private String turn;

	private String notes;

	@Column(name = "medical_record_id")
	private UUID medicalRecordId;

	private Integer disciplinaryReportCount;
}

// Tablas generadas a partir de este archivo:
/*
 * Grupo
 * Becas
 * Servicio Social
 * Practicas
 * Expediente medico
 * Reportes Disciplinarios
 */
