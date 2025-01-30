package com.alom.dorundorunbe.global.util.point.calculator;

import com.alom.dorundorunbe.domain.runningrecord.domain.RunningRecord;
import org.springframework.stereotype.Component;

@Component("doodleRunCalculator")
public class DoodleRunCalculator implements PointCalculator {
    //private final DoodleRunConfig doodleRunConfig;

//    public DoodleRunCalculator(DoodleRunConfig doodleRunConfig) {
//        this.doodleRunConfig = doodleRunConfig;
//    }



    @Override
    public double calculatePoints(RunningRecord record) {
        double basePoints = Math.pow(record.getDistance(), 1.3) / record.getElapsedTime();
        double weightedPoints = basePoints;

//        if (doodleRunConfig.isConsiderDistance()) {
//            weightedPoints *= getDistanceWeight(record.getDistance());
//        }
//        if (doodleRunConfig.isConsiderCadence()) {
//            weightedPoints *= getCadenceWeight(record.getCadence());
//        }
//        if (doodleRunConfig.isConsiderHeartRate()) {
//            weightedPoints *= getHeartRateWeight(record.getHeartRate());
//        }
//        if (doodleRunConfig.isConsiderLocation()) {
//            weightedPoints *= getLocationWeight(record.getLocationData());
//        }

        return weightedPoints;
    }

    private double getDistanceWeight(double distance) {
        double a = 0.05, b = 0.02;
        if (distance < 5) return 1 - a * (5 - distance);
        if (distance == 5) return 1.0;
        return 1 - b * (distance - 5);
    }

//    private double getCadenceWeight(int cadence) {
//        int targetCadence = doodleRunConfig.getTargetCadence();
//        if (Math.abs(cadence - targetCadence) <= 5) return 1.05;
//        if (Math.abs(cadence - targetCadence) <= 10) return 1.0;
//        return 0.95;
//    }
//
//    private double getHeartRateWeight(int heartRate) {
//        double targetZonePercent = doodleRunConfig.getHeartRateZonePercentage();
//        if (targetZonePercent >= 50) return 1.05;
//        if (targetZonePercent >= 30) return 1.0;
//        return 0.95;
//    }
//
//    private double getLocationWeight(LocationData location) {
//        return (location.isTargetLocation()) ? 1.05 : 1.0;
//    }
}
