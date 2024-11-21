package com.alom.dorundorunbe.domain.challenge.repository;

import com.alom.dorundorunbe.domain.challenge.domain.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
}
