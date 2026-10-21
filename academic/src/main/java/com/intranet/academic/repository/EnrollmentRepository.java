package com.intranet.academic.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.intranet.academic.entity.EnrollmentEntity;

public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, UUID> {
	@Query("select e from EnrollmentEntity e where e.student_user_id = ?1")
	List<EnrollmentEntity> findByStudent(UUID studentUserId);

	@Query("select e from EnrollmentEntity e where e.subject_id = ?1")
	List<EnrollmentEntity> findBySubject(UUID subjectId);
}
