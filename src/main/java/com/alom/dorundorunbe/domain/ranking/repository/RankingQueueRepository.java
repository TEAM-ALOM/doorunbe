package com.alom.dorundorunbe.domain.ranking.repository;

import com.alom.dorundorunbe.domain.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RankingQueueRepository extends JpaRepository<RankingQueue, Long> {

    boolean existsByUser(User user);

    @Query("SELECT rq FROM RankingQueue rq ORDER BY rq.averageElapsedTime")
    List<RankingQueue> findAllOrderedByElapsedTime(Pageable pageable);

    Optional<RankingQueue> findByUser(User user);
}

