package com.alom.dorundorunbe.domain.achievement.repository;

import com.alom.dorundorunbe.domain.achievement.domain.UserAchievement;
import com.alom.dorundorunbe.domain.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAchievementRepository extends JpaRepository<UserAchievement, Long> {
    List<UserAchievement> findAllByUser(User user); //my page 에서 사용
    boolean existsByUserIdAndAchievementId(Long userId, Long achievementId);
    Optional<UserAchievement> findByUserIdAndAchievementId(Long userId, Long achievementId);

    @EntityGraph(attributePaths = {"achievement"})
    Slice<UserAchievement> findAllSliceByUserId(Long userId, Pageable pageable);
}
