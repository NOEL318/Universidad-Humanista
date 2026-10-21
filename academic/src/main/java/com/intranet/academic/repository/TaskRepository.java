package com.intranet.academic.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.intranet.academic.entity.TaskEntity;

public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {
	@Query("select t from TaskEntity t where t.group_id = ?1 order by t.due_date")
	List<TaskEntity> findByGroup(UUID groupId);
}
