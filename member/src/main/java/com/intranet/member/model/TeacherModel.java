package com.intranet.member.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TeacherModel {
	public TeacherModel() {}

	private UUID id;
	@NotNull(message = "El usuario es un campo obligatorio")
	private UUID userId;
	@NotBlank(message = "Numero de empleado es un campo obligatorio")
	private String employeeNumber;
	private String department;
	private LocalDateTime hireDate;
	private String contractType;
	@NotBlank(message = "Estatus es un campo obligatorio")
	private String status; // active, license, jubilated, off
	private String specialization;
	private Integer totalhoursAssigned;
	private String officeLocation;
	private UUID medicalRecordId;
	private Integer disciplinaryReportCount;
	private String notes;
	private String turn;

	// Datos del usuario
	private UserModel user;
}
