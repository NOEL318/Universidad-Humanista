package com.intranet.member.repository;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.intranet.member.entity.MedicalRecordEntity;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecordEntity, UUID> {
    @Query("select m from MedicalRecordEntity m where m.user_id = ?1")
    Optional<MedicalRecordEntity> findByUserId(UUID userId);
}
