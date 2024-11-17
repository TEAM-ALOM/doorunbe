package com.alom.dorundorunbe.domain.user.repository;

import com.alom.dorundorunbe.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
