package com.intranet.user.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.intranet.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}
