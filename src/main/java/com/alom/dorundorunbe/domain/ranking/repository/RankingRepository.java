package com.alom.dorundorunbe.domain.ranking.repository;

import com.alom.dorundorunbe.domain.ranking.domain.Ranking;

import com.alom.dorundorunbe.global.enums.Tier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface RankingRepository extends JpaRepository<Ranking, Long> {
    boolean existsByTier(Tier tier);

    Optional<Ranking> findByTier(Tier tier);




}
