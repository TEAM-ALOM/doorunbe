package com.alom.dorundorunbe.domain.ranking.repository;

import com.alom.dorundorunbe.domain.ranking.domain.Ranking;
import com.alom.dorundorunbe.domain.user.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RankingRepository extends JpaRepository<Ranking, Long> {
    Optional<Ranking> findByUser(User user);
    boolean existsByUser(User user);

    @Override
    @EntityGraph(attributePaths = {"user"})
    List<Ranking>findAll();




}
