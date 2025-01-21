package com.alom.dorundorunbe.domain.point.service;

import com.alom.dorundorunbe.domain.point.domain.Point;
import com.alom.dorundorunbe.domain.point.domain.UserPoint;
import com.alom.dorundorunbe.domain.point.repository.PointRepository;
import com.alom.dorundorunbe.domain.point.repository.UserPointRepository;
import com.alom.dorundorunbe.domain.runningrecord.domain.RunningRecord;
import com.alom.dorundorunbe.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointService {
    private final PointCalculationService pointCalculationService;
    private final PointRepository pointRepository;
    private final UserPointRepository userPointRepository;
    @Transactional
    public void processRunningPoints(RunningRecord record) {
        User user = record.getUser();
        double rankingPoints = pointCalculationService.calculateRankingPoints(record);
        double doodlePoints = pointCalculationService.calculateDoodlePoints(record);

        saveUserPoint(user,rankingPoints,doodlePoints);

        Long total = (long)(rankingPoints + doodlePoints);

        user.addPoint(total);
    }

    /**
     * 포인트 저장
     */
    @Transactional
    public void saveUserPoint(User user, Double rankingPoints, Double doodlePoints) {
        // 포인트 객체 생성 후 저장
        Point point = Point.create(rankingPoints, doodlePoints);
        pointRepository.save(point);

        // 사용자 포인트 저장
        UserPoint userPoint = UserPoint.create(user, point);
        userPointRepository.save(userPoint);
    }

    /**
     * 사용자의 포인트 조회
     */
    public List<UserPoint> getUserPoints(User user) {
        return userPointRepository.findByUser(user);
    }

    /**
     * 사용자의 상위 3개 랭킹 포인트 조회
     */
    public List<UserPoint> getTop3RankingRecords(User user) {
        return userPointRepository.findTop3ByUserOrderByPoint_RankingPointDesc(user);
    }

    /**
     * 사용자의 포인트 삭제
     */
    @Transactional
    public void deleteUserPoints(User user) {
        userPointRepository.deleteByUser(user);
    }
}
