package com.alom.dorundorunbe.domain.achievement.repository;

import com.alom.dorundorunbe.domain.achievement.domain.UserAchievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAchievementRepository extends JpaRepository<UserAchievement, Long> {
    List<UserAchievement> findAllByUserName(String name);
}
