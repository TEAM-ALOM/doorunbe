package com.alom.dorundorunbe.domain.achievement.exception.common;

public class ErrorMessages {
    public static final String ACHIEVEMENT_ALREADY_EXISTS = "이미 존재하는 업적입니다: 이름 = %s, 조건 = %s";
    public static final String ACHIEVEMENT_NOT_FOUND = "업적을 찾을 수 없습니다. ID: %s";
    public static final String USER_NOT_FOUND = "사용자를 찾을 수 없습니다. ID: %s";
    public static final String USER_ACHIEVEMENT_ALREADY_CLAIMED = "이미 해당 업적을 받았습니다.";
    public static final String USER_ACHIEVEMENT_NOT_FOUND = "해당 사용자의 업적을 찾을 수 없습니다. ID: %s";
    public static final String ACHIEVEMENT_CONDITION_NOT_MET = "해당 업적을 받을 수 있는 조건이 충족되지 않았습니다.";
    public static final String REWARD_ALREADY_CLAIMED = "이미 보상을 수령한 업적입니다.";

}
