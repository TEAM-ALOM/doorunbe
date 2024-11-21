package com.alom.dorundorunbe.domain.challenge.repository;

import com.alom.dorundorunbe.domain.challenge.domain.UserChallenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserChallengeRepository extends JpaRepository<UserChallenge, Long> {
  Optional<UserChallenge> findByUserIdAndChallengeId(Long userId, Long challengeId);

  List<UserChallenge> findByChallengeId(Long challengeId);

  List<UserChallenge> findByUserId(Long userId);
}
