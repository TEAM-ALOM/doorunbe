package com.alom.dorundorunbe.domain.doodle.config;

import com.alom.dorundorunbe.domain.doodle.domain.Doodle;
import com.alom.dorundorunbe.domain.doodle.domain.UserDoodle;
import com.alom.dorundorunbe.domain.doodle.service.DoodleService;
import com.alom.dorundorunbe.domain.doodle.service.UserDoodleService;
import com.alom.dorundorunbe.domain.runningrecord.domain.RunningRecord;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DoodleRunConfig {

    private final UserDoodleService userDoodleService;

    public DoodleRunConfig(UserDoodleService userDoodleService) {
        this.userDoodleService = userDoodleService;
    }

    public boolean isConsiderDistance(Doodle doodle, List<RunningRecord> runningRecords){
        return userDoodleService.checkWeeklyTotalDistance(doodle, runningRecords);
    }

    public boolean isConsiderCadence(Doodle doodle, List<RunningRecord> runningRecords){
        return userDoodleService.checkWeeklyAverageCadence(doodle, runningRecords);
    }

    public boolean isConsiderHeartRate(Doodle doodle, List<RunningRecord> runningRecords){
        return userDoodleService.checkWeeklyAverageHeartRate(doodle, runningRecords);
    }

//    public boolean isConsiderLocation(Doodle doodle, List<RunningRecord> runningRecords){
//
//    }


}
