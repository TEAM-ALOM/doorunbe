package com.alom.dorundorunbe.domain.point.repository;

import com.alom.dorundorunbe.domain.point.domain.UserPoint;
import com.alom.dorundorunbe.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPointRepository extends JpaRepository<UserPoint, Long> {
    List<UserPoint> findByUser(User user);
    void deleteByUser(User user);

    List<UserPoint> findTop3ByUserOrderByPoint_RankingPointDesc(User user);
}
