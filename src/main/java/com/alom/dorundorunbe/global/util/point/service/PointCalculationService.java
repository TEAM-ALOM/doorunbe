package com.alom.dorundorunbe.global.util.point.service;

import com.alom.dorundorunbe.domain.runningrecord.domain.RunningRecord;
import com.alom.dorundorunbe.global.util.point.calculator.PointCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PointCalculationService {



    private final Map<String, PointCalculator> pointCalculators;



    public double calculatePoints(String calculatorName, RunningRecord record) {
        PointCalculator calculator = pointCalculators.get(calculatorName);
        if (calculator == null) {
            throw new IllegalArgumentException("Unsupported calculator: " + calculatorName);
        }
        return calculator.calculatePoints(record);
    }


}
