package com.nishikant.user_service.repository;


import com.nishikant.user_service.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User>  findByUsername(String username);
}
