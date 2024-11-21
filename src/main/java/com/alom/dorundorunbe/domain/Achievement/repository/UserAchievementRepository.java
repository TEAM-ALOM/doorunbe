package com.alom.dorundorunbe.domain.Achievement.repository;

import com.alom.dorundorunbe.domain.Achievement.domain.UserAchievement;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAchievementRepository extends JpaRepository<UserAchievement, Long> {
    boolean existsByUserIdAndAchievementId(Long userId, Long achievementId);

    @EntityGraph(attributePaths = {"achievement"})
    List<UserAchievement> findByUserId(Long userId);

    @EntityGraph(attributePaths = {"achievement"})
    Slice<UserAchievement> findAllSliceByUserId(Long userId, Pageable pageable);

    Optional<UserAchievement> findByUserIdAndAchievementId(Long userId, Long achievementId);




}
