package com.alom.dorundorunbe.domain.ranking.service;

import com.alom.dorundorunbe.domain.RunningRecord.repository.RunningRecordRepository;
import com.alom.dorundorunbe.domain.ranking.domain.RankingQueue;
import com.alom.dorundorunbe.domain.ranking.dto.create.CreateRankingResponseDto;
import com.alom.dorundorunbe.domain.ranking.repository.RankingQueueRepository;
import com.alom.dorundorunbe.domain.ranking.repository.RankingRepository;
import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class RankingQueueService {
    private final RankingQueueRepository rankingQueueRepository;
    private final RankingRepository rankingRepository;
    private final UserRepository userRepository;
    private final RunningRecordRepository runningRecordRepository;
    private final Clock clock;

    @Transactional
    public CreateRankingResponseDto joinQueue(Long userId) {

        return executeJoinQueue(userId);


    }

    private CreateRankingResponseDto executeJoinQueue(Long userId) {
        if (!isRegistrationOpen()) {
            throw new IllegalStateException("참가 신청은 월요일 새벽 1시부터 오후 5시까지만 가능합니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (rankingQueueRepository.existsByUser(user)) {
            throw new IllegalStateException("사용자가 이미 대기열에 있습니다.");
        }

        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        long averageElapsedTime = runningRecordRepository.findMonthlyAverageElapsedTime(user, oneMonthAgo)
                .map(Double::longValue)
                .orElse(Long.MAX_VALUE);
        RankingQueue queue = RankingQueue.builder()
                .user(user)
                .averageElapsedTime(averageElapsedTime)
                .build();

        RankingQueue savedQueue = rankingQueueRepository.save(queue);
        return CreateRankingResponseDto.of(
                savedQueue.getId(),
                user.getName(),
                savedQueue.getCreatedAt()
        );
    }

    /**
     * 참가 신청 가능 시간 확인 메서드 (월요일 새벽 1시 ~ 오후 5시)
     */
    private boolean isRegistrationOpen() {
        LocalDateTime now = LocalDateTime.now(clock);
        return now.getDayOfWeek() == java.time.DayOfWeek.MONDAY // 월요일인지 확인
                && now.getHour() >= 1
                && now.getHour() < 17;
    }
}
