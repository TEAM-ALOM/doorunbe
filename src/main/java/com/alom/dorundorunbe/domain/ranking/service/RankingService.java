package com.alom.dorundorunbe.domain.ranking.service;

import com.alom.dorundorunbe.global.enums.Tier;
import com.alom.dorundorunbe.domain.ranking.domain.Ranking;
import com.alom.dorundorunbe.domain.ranking.exception.RankingNotFoundException;
import com.alom.dorundorunbe.domain.ranking.exception.UserAlreadyJoinedRankingException;
import com.alom.dorundorunbe.domain.ranking.exception.UserNotFoundException;
import com.alom.dorundorunbe.domain.ranking.repository.RankingRepository;
import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.alom.dorundorunbe.domain.ranking.exception.common.ErrorMessages.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RankingService {
        private final UserRepository userRepository;
        private final RankingRepository rankingRepository;

        @Transactional
        public Long joinRanking(Long userId){
                User user = findUserById(userId);
                if(rankingRepository.existsByUser(user)){
                        throw new UserAlreadyJoinedRankingException(USER_ALREADY_JOINED);
                }
                Ranking ranking = Ranking.builder()
                        .user(user)
                        .build();
                rankingRepository.save(ranking);
                return ranking.getId();

        }


        @Transactional
        public Long updateRanking(Long userId, Tier tier, long distance, long time, int cadence, int grade){
                User user = findUserById(userId);
                Ranking ranking = findRankingByUser(user);
                ranking.updateRanking(tier, distance, time, cadence, grade);
                return ranking.getId();

        }

        public List<Ranking> fetchAllRankings() {
                return rankingRepository.findAll();
        }
        //조회 목적
        public Ranking findOne(Long rankId)
        {
            return rankingRepository.findById(rankId).orElseThrow(
                        () -> new RankingNotFoundException(RANKING_NOT_FOUND)
                );

        }


        private Ranking findRankingByUser(User user) {
                return rankingRepository.findByUser(user).orElseThrow(
                        () -> new RankingNotFoundException(RANKING_NOT_FOUND)
                );

        }

        private User findUserById(Long userId) {
                return userRepository.findById(userId).orElseThrow(
                        () -> new UserNotFoundException(USER_NOT_FOUND)
                );

        }









}
