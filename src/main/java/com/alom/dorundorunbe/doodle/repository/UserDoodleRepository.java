package com.alom.dorundorunbe.doodle.repository;

import com.alom.dorundorunbe.doodle.domain.UserDoodle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDoodleRepository extends JpaRepository<UserDoodle, Long> {
    Optional<UserDoodle> findByDoodleAndUser(Long doodleId, Long userId);

}
