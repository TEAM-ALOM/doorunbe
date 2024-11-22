package com.alom.dorundorunbe.domain.Achievement.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    List<UserAchievement> findAllByName(String name);
}
