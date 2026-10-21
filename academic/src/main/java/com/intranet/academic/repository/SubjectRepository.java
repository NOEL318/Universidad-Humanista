package com.intranet.academic.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.intranet.academic.entity.SubjectEntity;

public interface SubjectRepository extends JpaRepository<SubjectEntity, UUID> {
	@Query("select s from SubjectEntity s where s.group_id = ?1")
	List<SubjectEntity> findByGroup(UUID groupId);
}
