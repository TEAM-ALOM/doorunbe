package com.alom.dorundorunbe.domain.user.repository;

import com.alom.dorundorunbe.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
  Optional<User> findByName(String username);
  boolean existsByName(String username);
  boolean existsByPassword(String password);
  boolean existsByNickname(String nickName);
}