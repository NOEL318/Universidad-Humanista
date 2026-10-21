package com.intranet.academic.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intranet.academic.entity.GroupEntity;

public interface GroupRepository extends JpaRepository<GroupEntity, UUID> {
}
