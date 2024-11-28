package com.alom.dorundorunbe.domain.achievement.repository;

import com.alom.dorundorunbe.domain.achievement.domain.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AchievementRepository extends JpaRepository<Achievement,Long> {
    Optional<Achievement> findByName(String name);
}
