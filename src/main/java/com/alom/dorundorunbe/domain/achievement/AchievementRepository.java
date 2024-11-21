package com.alom.dorundorunbe.domain.achievement;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    List<UserAchievement> findAllByUserName(String name);
}
