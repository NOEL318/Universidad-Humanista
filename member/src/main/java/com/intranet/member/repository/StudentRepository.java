package com.intranet.member.repository;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.intranet.member.entity.StudentEntity;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {
    @Query("select s from StudentEntity s where s.user_id = ?1")
    Optional<StudentEntity> findByUserId(UUID userId);
}