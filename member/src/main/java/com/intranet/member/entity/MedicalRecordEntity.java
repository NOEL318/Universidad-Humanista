package com.intranet.member.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

// Expediente medico de alumnos y maestros (ver user/readme.md)
@Data
@Entity
@Table(name = "medical_record")
public class MedicalRecordEntity {
	public MedicalRecordEntity() {}

	@Id
	@GeneratedValue
	@Column(updatable = false, nullable = false)
	private UUID id;

	@Column(unique = true, nullable = false)
	private UUID user_id;

	private Boolean disability;
	private String disability_type;
	private String chronic_conditions;
	private String mental_health_conditions;

	private String blood_type;
	private String allergies;
	private String emergency_contact_name;
	private String emergency_contact_relationship;
	private String emergency_contact_phone;

	private Boolean has_health_insurance;
	private String insurance_provider;
	private String insurance_policy_number;
	private String insurance_valid_until;
	private String medications;

	private Boolean mobility_aid;
	private Boolean vision_problems;
	private Boolean hearing_problems;

	private String notes;

	@UpdateTimestamp
	private LocalDateTime updated_at;
}
