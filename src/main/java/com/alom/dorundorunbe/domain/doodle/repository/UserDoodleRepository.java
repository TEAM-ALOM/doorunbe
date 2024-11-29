package com.alom.dorundorunbe.domain.doodle.repository;

import com.alom.dorundorunbe.domain.doodle.domain.UserDoodle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDoodleRepository extends JpaRepository<UserDoodle, Long> {
    Optional<UserDoodle> findByDoodleIdAndUserId(Long doodleId, Long userId);

}
