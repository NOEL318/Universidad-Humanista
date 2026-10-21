package com.intranet.auth.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intranet.auth.entity.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {
	Optional<AccountEntity> findByEmail(String email);
}
