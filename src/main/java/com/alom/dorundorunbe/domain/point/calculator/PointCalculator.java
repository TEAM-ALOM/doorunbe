package com.alom.dorundorunbe.domain.point.calculator;

import com.alom.dorundorunbe.domain.runningrecord.domain.RunningRecord;

public interface PointCalculator {
    boolean supports(RunningRecord record);
    double calculatePoints(RunningRecord record);
}
