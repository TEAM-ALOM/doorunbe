package com.alom.dorundorunbe.domain.ranking.service;

import com.alom.dorundorunbe.domain.runningrecord.repository.RunningRecordRepository;
import com.alom.dorundorunbe.domain.ranking.domain.Ranking;
import com.alom.dorundorunbe.domain.ranking.domain.RankingQueue;
import com.alom.dorundorunbe.domain.ranking.dto.create.CreateRankingResponseDto;
import com.alom.dorundorunbe.domain.ranking.dto.delete.DeleteRankingResponseDto;
import com.alom.dorundorunbe.domain.ranking.repository.RankingQueueRepository;
import com.alom.dorundorunbe.domain.ranking.repository.RankingRepository;
import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

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

    @Transactional
    public DeleteRankingResponseDto cancelQueue(Long userId) {
        return executeCancelQueue(userId);
    }

    private DeleteRankingResponseDto executeCancelQueue(Long userId) {
        if (!isRegistrationOpen()) {
            throw new IllegalStateException("참가 신청 해제는 월요일 새벽 1시부터 오후 5시까지만 가능합니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        RankingQueue queue = rankingQueueRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("사용자가 대기열에 존재하지 않습니다."));

        rankingQueueRepository.delete(queue);
        log.info("랭킹 대기열에서 사용자 제거: 사용자 ID={}", userId);
        return DeleteRankingResponseDto.of(
                queue.getId(),
                user.getName(),
                queue.getCreatedAt(),
                LocalDateTime.now()
        );
    }

    @Transactional
    @Scheduled(cron = "0 0 17 ? * MON") // 매주 월요일 오후 5시에 실행
    public void processRankingQueue() {
        int pageNumber = 0;
        int pageSize = 10; // 한 번에 처리할 그룹 크기
        boolean hasMoreData = true;

        while (hasMoreData) {
            // 페이징 처리를 통해 대기열 데이터를 가져옴
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            List<RankingQueue> group = rankingQueueRepository.findAllOrderedByElapsedTime(pageable);

            if (group.isEmpty()) {
                hasMoreData = false; // 더 이상 데이터가 없으면 루프 종료
                continue;
            }

            if (group.size() >= 4) {
                createRanking(group); // 방 생성
            } else {
                handleFailedParticipants(group); // 4명 미만 실패 처리
            }

            pageNumber++; // 다음 페이지로 이동
        }
    }

    private void createRanking(List<RankingQueue> group) {
        Ranking ranking = Ranking.builder()
                .isFinished(false)
                .build();

        rankingRepository.save(ranking);

        group.forEach(queue -> {
            User user = queue.getUser();
            ranking.addParticipant(user);
            rankingQueueRepository.delete(queue);
        });

        log.info("랭킹 방 생성 완료: 참가자 수 = {}", group.size());
    }
    private void handleFailedParticipants(List<RankingQueue> failedGroup) {
        failedGroup.forEach(queue -> {
            User user = queue.getUser();
            rankingQueueRepository.delete(queue);
            log.info("참가 실패: 사용자 ID={}, 평균 기록={}", user.getId(), queue.getAverageElapsedTime());

        });
    }
}
