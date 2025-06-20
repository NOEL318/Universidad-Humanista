package com.intranet.member.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

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

	private UUID user_id;

	private String enrollment_number; // matrícula

	private String academic_program;

	private String semester;

	private String status; // active, suspended, graduated, off

	private UUID advisor_id; // asesor o tutor académico

	private Double score;
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime enrollment_date; // fecha de inscripción

	private LocalDateTime graduation_date; // fecha o estimación de graduación
	private UUID group_id;

	private Boolean scholarship; // tiene beca? true/false

	private UUID scholarship_id; // ID de la tabla becas en caso de tener beca

	private String service_social_status;

	private String internship_status;

	private String turn;

	private String notes;
	private UUID medical_record_id;

	private Integer disciplinary_report_count;
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
