package com.alom.dorundorunbe.domain.ranking.repository;

import com.alom.dorundorunbe.domain.ranking.domain.Ranking;
import com.alom.dorundorunbe.domain.ranking.domain.UserRanking;
import com.alom.dorundorunbe.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRankingRepository extends JpaRepository<UserRanking, Long> {
    Optional<UserRanking> findByUserAndRanking(User user, Ranking ranking);

    List<UserRanking> findByRanking(Ranking ranking);
}
