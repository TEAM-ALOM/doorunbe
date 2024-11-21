package com.alom.dorundorunbe.domain.mypage.dto;

import com.alom.dorundorunbe.domain.RunningRecord.domain.RunningRecord;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MyPageResponse {

    private String name;
    private String email;
    private List<AchievementResponse> achievements;
    private String rank;
    private List<RunningRecord> runningRecords;

    public MyPageResponse(String name, String email, List<AchievementResponse> achievements, String rank,
                          List<RunningRecord> runningRecords) {
        this.name = name;
        this.email = email;
        this.achievements = achievements;
        this.rank = rank;
        this.runningRecords = runningRecords;
    }
}
