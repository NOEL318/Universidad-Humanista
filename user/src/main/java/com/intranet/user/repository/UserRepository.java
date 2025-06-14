package com.intranet.user.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import com.intranet.user.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}
