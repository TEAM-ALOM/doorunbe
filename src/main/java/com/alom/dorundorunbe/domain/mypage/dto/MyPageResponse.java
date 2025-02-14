package com.alom.dorundorunbe.domain.mypage.dto;

import com.alom.dorundorunbe.domain.runningrecord.domain.RunningRecord;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MyPageResponse {

    private String email;
    private String nickname;
    private List<AchievementResponse> achievements;
    private String rank;
    private List<RunningRecord> runningRecords;

    public MyPageResponse(String email, String nickname, List<AchievementResponse> achievements, String rank,
                          List<RunningRecord> runningRecords) {
        this.email = email;
        this.nickname = nickname;
        this.achievements = achievements;
        this.rank = rank;
        this.runningRecords = runningRecords;
    }
}
