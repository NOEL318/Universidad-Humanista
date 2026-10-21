package com.intranet.academic.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.intranet.academic.entity.GradeEntity;

public interface GradeRepository extends JpaRepository<GradeEntity, UUID> {
	@Query("select g from GradeEntity g where g.student_user_id = ?1")
	List<GradeEntity> findByStudent(UUID studentUserId);

	@Query("select g from GradeEntity g where g.subject_id = ?1")
	List<GradeEntity> findBySubject(UUID subjectId);
}
