package com.alom.dorundorunbe.domain.achievement.repository;

import com.alom.dorundorunbe.domain.achievement.domain.UserAchievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAchievementRepository extends JpaRepository<UserAchievement, Long> {
    List<UserAchievement> findAllByUserName(String name); //my page 에서 사용
    boolean existsByUserIdAndAchievementId(Long userId, Long achievementId);
    Optional<UserAchievement> findByUserIdAndAchievementId(Long userId, Long achievementId);
}
