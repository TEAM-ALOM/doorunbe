package com.alom.dorundorunbe.domain.Achievement.service;


import com.alom.dorundorunbe.domain.Achievement.domain.Achievement;
import com.alom.dorundorunbe.domain.Achievement.domain.RewardType;
import com.alom.dorundorunbe.domain.Achievement.domain.UserAchievement;
import com.alom.dorundorunbe.domain.Achievement.dto.query.AchievementDto;
import com.alom.dorundorunbe.domain.Achievement.exception.*;
import com.alom.dorundorunbe.domain.Achievement.exception.common.ErrorMessages;
import com.alom.dorundorunbe.domain.Achievement.repository.AchievementRepository;
import com.alom.dorundorunbe.domain.Achievement.repository.UserAchievementRepository;
import com.alom.dorundorunbe.domain.RunningRecord.repository.RunningRecordRepository;
import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AchievementService {
    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final UserRepository userRepository;
    private final RunningRecordRepository runningRecordRepository;

    @Transactional
    public Long createAchievement(String name, RewardType rewardType, String rewardValue, String condition) {
        // 이름과 조건으로 중복 여부 확인
        achievementRepository.findByNameAndCondition(name, condition).ifPresent(existing -> {
            throw new AchievementAlreadyExistsException(
                    String.format(ErrorMessages.ACHIEVEMENT_ALREADY_EXISTS, name, condition)
            );
        });
        Achievement achievement = makeAchievement(name, rewardType, rewardValue, condition);
        return achievement.getId();
    }

    @Transactional
    public void updateAchievement(Long id, String name, String rewardValue) {
        Achievement achievement = achievementRepository.findById(id)
                .orElseThrow(() -> new AchievementNotFoundException(
                        String.format(ErrorMessages.ACHIEVEMENT_NOT_FOUND, id)));
        updateNameRewardValueIfExist(name, rewardValue, achievement);

    }




    public Slice<AchievementDto> fetchAllUserAchievements(Long userId, Pageable pageable) {
        Slice<UserAchievement> userAchievementsSlice = userAchievementRepository.findAllSliceByUserId(userId, pageable);
        return userAchievementsSlice.map(userAchievement ->
                AchievementDto.of(userAchievement.getAchievement()));
    }


    /**
     * 업적 조건 평가 및 할당(보상 지급)
     */
    @Transactional
    public Long checkAndAssignAchievement(Long userId, Long achievementId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException(String.format(ErrorMessages.USER_NOT_FOUND, userId)
        ));
        // 업적 조회
        Achievement achievement = achievementRepository.findById(achievementId)
                .orElseThrow(() -> new AchievementNotFoundException(
                        String.format(ErrorMessages.ACHIEVEMENT_NOT_FOUND, achievementId)));

        // 이미 해당 업적을 받았는지 확인
        if (userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId)) {
            throw new UserAchievementAlreadyClaimedException(ErrorMessages.USER_ACHIEVEMENT_ALREADY_CLAIMED);
        }

        // 조건 검증
        if (!evaluateCondition(user, achievement.getCondition())) {
            throw new AchievementConditionNotMetException(ErrorMessages.ACHIEVEMENT_CONDITION_NOT_MET);
        }

        UserAchievement userAchievement = assignAchievementToUser(user, achievement);

        return userAchievement.getId();


    }


    @Transactional
    public Long claimReward(Long userId, Long achievementId) {
        UserAchievement userAchievement = userAchievementRepository.findByUserIdAndAchievementId(userId, achievementId)
                .orElseThrow(() -> {
                            String errorMessage = String.format(
                                    ErrorMessages.USER_ACHIEVEMENT_NOT_FOUND,
                                    "userId: " + userId + ", achievementId: " + achievementId // 상세 정보 제공
                            );
                    return new UserAchievementNotFoundException(errorMessage);
                });

        if (userAchievement.isRewardClaimed()) {
            throw new RewardAlreadyClaimedException(ErrorMessages.REWARD_ALREADY_CLAIMED);
        }

        Achievement achievement = userAchievement.getAchievement();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format(ErrorMessages.USER_NOT_FOUND, userId)
                ));

        giveRewardToUser(achievement, user);

        userAchievement.markRewardAsClaimed();//isRewardClaimed = true로 change(수령했음을 의미)
        return userAchievement.getId();
    }

    //단순 조회용
    public Achievement findOneAchievement(Long achievementId){
        return achievementRepository.findById(achievementId).orElseThrow(
                ()->new AchievementNotFoundException(String.format(ErrorMessages.ACHIEVEMENT_NOT_FOUND, achievementId))
        );
    }
    //단순 조회용
    public UserAchievement findOneUserAchievement(Long userAchievementId){
        return userAchievementRepository.findById(userAchievementId).orElseThrow(
                ()->new UserAchievementNotFoundException(String.format(ErrorMessages.USER_ACHIEVEMENT_NOT_FOUND, userAchievementId))//문제
        );
    }

    private void updateNameRewardValueIfExist(String name, String rewardValue, Achievement achievement) {
        // name 업데이트
        if (name != null && !name.isEmpty()) {
            achievement.updateName(name);
        }

        // rewardValue 업데이트
        if (rewardValue != null && !rewardValue.isEmpty()) {
            achievement.updateRewardValue(rewardValue);
        }
    }

    private void giveRewardToUser(Achievement achievement, User user) {
        if (achievement.getRewardType() == RewardType.COIN) {
            user.addCash(Integer.parseInt(achievement.getRewardValue()));
        } else if (achievement.getRewardType() == RewardType.BACKGROUND) {
            user.updateBackground(achievement.getRewardValue());
        }
    }


    /**
     * 업적 조건 평가
     */
    //User단에 background, lp, Rank 필드 추가 필요해보인다
    private boolean evaluateCondition(User user, String condition) {
        //캐쉬 관련
        if (condition.contains("totalDistance")) {//"totalDistance>=50" 형태 condition입력
            double totalDistance = runningRecordRepository.findTotalDistanceByUserId(user.getId());
            int conditionValue = Integer.parseInt(condition.replaceAll("[^0-9]", ""));
            return totalDistance >= conditionValue;
        }

        if (condition.contains("averageCadence")) {//"averageCadence=170~180"
            double averageCadence = runningRecordRepository.findAverageCadenceByUserId(user.getId());
            String[] range = condition.replaceAll("[^0-9~]", "").split("~");
            double min = Double.parseDouble(range[0]);
            double max = Double.parseDouble(range[1]);
            return averageCadence >= min && averageCadence <= max;
        }

        if (condition.contains("weeklyRuns")) {//"weeklyRuns>=3"
            long weeklyRuns = getWeeklyRuns(user.getId());
            int conditionValue = Integer.parseInt(condition.replaceAll("[^0-9]", ""));
            return weeklyRuns >= conditionValue;
        }
        //background관련 "rank=BEGINNER" 형태
//        if (condition.contains("rank")) {
//            Rank userRank = user.getRank();
//            String requiredRank = condition.split("=")[1].trim(); //
//            return userRank.name().equalsIgnoreCase(requiredRank);
//        }

        return false;
    }

    private long getWeeklyRuns(Long userId) {
        LocalDateTime startOfWeek = LocalDateTime.now().minusDays(7);
        return runningRecordRepository.countRecordsByUserIdAndDateRange(userId, startOfWeek, LocalDateTime.now());
    }

    /**
     * 업적 할당
     */
    private UserAchievement assignAchievementToUser(User user, Achievement achievement) {

        UserAchievement userAchievement = UserAchievement.builder().user(user)
                .achievement(achievement)
                .rewardClaimed(false)
                .build();

        return userAchievementRepository.save(userAchievement);
    }

    private Achievement makeAchievement(String name, RewardType rewardType, String rewardValue, String condition) {
        Achievement achievement = Achievement.builder()
                .name(name)
                .rewardType(rewardType)
                .rewardValue(rewardValue)
                .condition(condition)
                .build();
        return achievementRepository.save(achievement);

    }





}
