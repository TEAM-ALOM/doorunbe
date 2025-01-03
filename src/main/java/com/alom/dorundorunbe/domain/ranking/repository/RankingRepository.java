package com.alom.dorundorunbe.domain.ranking.repository;

import com.alom.dorundorunbe.domain.ranking.domain.Ranking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface RankingRepository extends JpaRepository<Ranking, Long> {
    @Query("SELECT r FROM Ranking r WHERE r.isFinished = false")
    List<Ranking> findAllActiveRankings();

    @Query("SELECT r FROM Ranking r WHERE r.isFinished = false")
    Page<Ranking> findRankings(Pageable pageable);




}
