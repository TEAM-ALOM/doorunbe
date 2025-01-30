package com.alom.dorundorunbe.domain.ranking.service;

import com.alom.dorundorunbe.domain.ranking.domain.Ranking;
import com.alom.dorundorunbe.domain.ranking.domain.UserRanking;
import com.alom.dorundorunbe.domain.ranking.repository.UserRankingRepository;
import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.global.exception.BusinessException;
import com.alom.dorundorunbe.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserRankingService {
    private final UserRankingRepository userRankingRepository;
    private final SimpMessagingTemplate messagingTemplate;;



    public void createUserRanking(User user, Ranking ranking){

        boolean alreadyParticipated = userRankingRepository.existsByUserAndRanking(user, ranking);
        if (alreadyParticipated) {
            throw new BusinessException(ErrorCode.USER_ALREADY_IN_RANKING);
        }


        UserRanking userRanking = UserRanking.create(user, ranking);
        userRanking.confirmRanking(ranking);
        userRankingRepository.save(userRanking);



    }