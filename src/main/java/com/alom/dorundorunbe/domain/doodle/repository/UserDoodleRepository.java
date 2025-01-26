package com.alom.dorundorunbe.domain.doodle.repository;

import com.alom.dorundorunbe.domain.doodle.domain.Doodle;
import com.alom.dorundorunbe.domain.doodle.domain.UserDoodle;
import com.alom.dorundorunbe.domain.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDoodleRepository extends JpaRepository<UserDoodle, Long> {
    Optional<UserDoodle> findByDoodleAndUser(Doodle doodle, User user);

    @Query("SELECT ud.doodle FROM UserDoodle ud WHERE ud.user =:user")
    List<Doodle> findAllDoodleByUser(@Param("user") User user); //유저가 참여한 모든 Doodle 반환

    @Query("SELECT ud.doodle FROM UserDoodle ud WHERE ud.user =: user ORDER BY ud.doodle.doodlePoint DESC")
    List<Doodle> findTop10ByUserOrderByDoodlePointDesc(@Param("user") User user, Pageable pageable); //유저가 참여한 Doodle 중 포인트 상위 10개 방 반환
}
