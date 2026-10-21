package com.intranet.member.repository;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intranet.member.entity.TeacherEntity;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity, UUID> {
    Optional<TeacherEntity> findByUserId(UUID userId);
}
