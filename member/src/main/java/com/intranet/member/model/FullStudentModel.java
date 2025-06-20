package com.intranet.member.model;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class FullStudentModel {

	// Campos StudentModel
	private UUID id;
	private UUID user_id;
	private String enrollment_number;
	private String academic_program;
	private String semester;
	private UUID advisor_id;
	private LocalDateTime enrollment_date;
	private LocalDateTime graduation_date;
	private UUID group_id;
	private Boolean scholarship;
	private UUID scholarship_id;
	private String service_social_status;
	private String internship_status;
	private String turn;
	private Double score;
	private String notes;
	private UUID medical_record_id;
	private Integer disciplinary_report_count;
	// Campos UserModel
	private String name;
	private String father_surname;
	private String mother_surname;
	private String born_date;
	private String phone;
	private String sex;
	private String address;
	private String photo_url;
	private String role;
	private String curp;
	private String email;
	private String status;
	private LocalDateTime registry_date;
	private String access_card_code;

	// Constructor vacío
	public FullStudentModel() {
	}

	// Constructor que combina los dos modelos
	public FullStudentModel(StudentModel student, UserModel user) {
		this.id = student.getId();
		this.user_id = student.getUser_id();
		this.enrollment_number = student.getEnrollment_number();
		this.academic_program = student.getAcademic_program();
		this.semester = student.getSemester();
		this.status = student.getStatus();
		this.advisor_id = student.getAdvisor_id();
		this.enrollment_date = student.getEnrollment_date();
		this.graduation_date = student.getGraduation_date();
		this.group_id = student.getGroup_id();
		this.scholarship = student.getScholarship();
		this.scholarship_id = student.getScholarship_id();
		this.service_social_status = student.getService_social_status();
		this.internship_status = student.getInternship_status();
		this.turn = student.getTurn();
		this.score = student.getScore();
		this.notes = student.getNotes();
		this.medical_record_id = student.getMedical_record_id();
		this.disciplinary_report_count = student.getDisciplinary_report_count();
		this.name = user.getName();
		this.father_surname = user.getFather_surname();
		this.mother_surname = user.getMother_surname();
		this.born_date = user.getBorn_date();
		this.phone = user.getPhone();
		this.sex = user.getSex();
		this.address = user.getAddress();
		this.photo_url = user.getPhoto_url();
		this.role = user.getRole();
		this.curp = user.getCurp();
		this.email = user.getEmail();
		this.registry_date = user.getRegistry_date();
		this.access_card_code = user.getAccess_card_code();
	}
}
