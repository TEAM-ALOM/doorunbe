package com.alom.dorundorunbe.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String username);
    boolean existsByName(String username);
    boolean existsByPassword(String password);
    boolean existsByNickName(String nickName);
}
