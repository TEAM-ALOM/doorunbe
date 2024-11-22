package com.alom.dorundorunbe.domain.Achievement.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAchievementRepository extends JpaRepository<UserAchievement, Long> {
    List<UserAchievement> findAllByUserId(Long userId);
}
