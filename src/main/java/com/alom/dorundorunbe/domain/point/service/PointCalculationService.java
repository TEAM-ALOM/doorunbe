package com.alom.dorundorunbe.domain.point.service;

import com.alom.dorundorunbe.domain.point.calculator.PointCalculator;
import com.alom.dorundorunbe.domain.runningrecord.domain.RunningRecord;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointCalculationService {
    private final List<PointCalculator> calculators;

    public PointCalculationService(List<PointCalculator> calculators) {
        this.calculators = calculators;
    }


    /**
     * 개별 포인트 계산 (RankingRun)
     */
    public double calculateRankingPoints(RunningRecord record) {
        return calculators.stream()
                .filter(calculator -> calculator instanceof com.alom.dorundorunbe.domain.point.calculator.RankingRunCalculator)
                .filter(calculator -> calculator.supports(record))
                .mapToDouble(calculator -> calculator.calculatePoints(record))
                .findFirst()
                .orElse(0.0);
    }
}