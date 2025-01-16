package com.alom.dorundorunbe.domain.point.calculator;

import com.alom.dorundorunbe.domain.point.calculator.PointCalculator;
import com.alom.dorundorunbe.domain.runningrecord.domain.RunningRecord;
import org.springframework.stereotype.Component;

@Component
public class RankingRunCalculator implements PointCalculator {

    @Override
    public boolean supports(RunningRecord record) {
        return record.getUser().isParticipatingInRankingRun();

    }

    @Override
    public double calculatePoints(RunningRecord record) {
        return Math.pow(record.getDistance(), 1.3) / record.getElapsedTime();
    }
}
