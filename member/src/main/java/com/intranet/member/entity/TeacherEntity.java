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
@Table(name = "teacher")
public class TeacherEntity {
	public TeacherEntity() {}

	@Id
	@GeneratedValue
	@Column(updatable = false, nullable = false)
	private UUID id;
	private UUID userId;
	private String employeeNumber; // matricula
	private String department;
	private LocalDateTime hireDate; // fecha de contratacion
	private String contractType;
	private String status; // active, license, jubilated, off
	private String specialization;
	private Integer totalhoursAssigned;
	private String officeLocation;
	private UUID medicalRecordId;
	private Integer disciplinaryReportCount;
	private String notes;
	private String turn;
}
