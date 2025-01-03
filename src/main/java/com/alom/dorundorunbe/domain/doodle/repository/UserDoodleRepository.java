package com.alom.dorundorunbe.domain.doodle.repository;

import com.alom.dorundorunbe.domain.doodle.domain.Doodle;
import com.alom.dorundorunbe.domain.doodle.domain.UserDoodle;
import com.alom.dorundorunbe.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDoodleRepository extends JpaRepository<UserDoodle, Long> {
    Optional<UserDoodle> findByDoodleAndUser(Doodle doodle, User user);

}
