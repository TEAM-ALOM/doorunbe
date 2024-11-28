package com.alom.dorundorunbe.domain.doodle.repository;

import com.alom.dorundorunbe.domain.doodle.domain.Doodle;
import com.alom.dorundorunbe.domain.doodle.domain.UserDoodle;
import com.alom.dorundorunbe.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDoodleRepository extends JpaRepository<UserDoodle, Long> {
    Optional<UserDoodle> findByDoodleAndUser(Doodle doodle, User user);

}
