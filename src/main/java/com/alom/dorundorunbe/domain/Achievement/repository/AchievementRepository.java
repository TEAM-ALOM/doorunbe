package com.alom.dorundorunbe.domain.Achievement.repository;

import com.alom.dorundorunbe.domain.Achievement.domain.Achievement;
import com.alom.dorundorunbe.domain.Achievement.domain.RewardType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    Optional<Achievement> findByNameAndCondition(String name, String condition);

}
