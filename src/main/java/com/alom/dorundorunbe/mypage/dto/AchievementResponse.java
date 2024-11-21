package com.alom.dorundorunbe.mypage.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@AllArgsConstructor
public class AchievementResponse {
    private Long achievementId;
    private String achievementName;
    private String achievementDescription;
}
