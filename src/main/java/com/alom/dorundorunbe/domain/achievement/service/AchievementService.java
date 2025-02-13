package com.alom.dorundorunbe.domain.achievement.service;

import com.alom.dorundorunbe.domain.runningrecord.repository.RunningRecordRepository;
import com.alom.dorundorunbe.domain.achievement.domain.Achievement;
import com.alom.dorundorunbe.domain.achievement.domain.RewardType;
import com.alom.dorundorunbe.domain.achievement.domain.UserAchievement;
import com.alom.dorundorunbe.domain.achievement.dto.assign.AssignAchievementRequestDto;
import com.alom.dorundorunbe.domain.achievement.dto.create.CreateAchievementRequestDto;
import com.alom.dorundorunbe.domain.achievement.dto.query.AchievementDto;
import com.alom.dorundorunbe.domain.achievement.dto.query.UserAchievementDto;
import com.alom.dorundorunbe.domain.achievement.dto.reward.RewardAchievementRequestDto;
import com.alom.dorundorunbe.domain.achievement.dto.update.UpdateAchievementRequestDto;
import com.alom.dorundorunbe.domain.achievement.repository.AchievementRepository;
import com.alom.dorundorunbe.domain.achievement.repository.UserAchievementRepository;
import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.domain.user.repository.UserRepository;
import com.alom.dorundorunbe.global.exception.BusinessException;
import com.alom.dorundorunbe.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AchievementService {
    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final UserRepository userRepository;
    private final RunningRecordRepository runningRecordRepository;

    @Transactional
    public Long createAchievement(CreateAchievementRequestDto requestDto) {
        // 이름 중복 확인
        achievementRepository.findByName(requestDto.name()).ifPresent(existing -> {
            throw new BusinessException(ErrorCode.ACHIEVEMENT_ALREADY_EXISTS);
        });

        String normalizedBackground = makeStringToLowerCase(requestDto.background());
        Achievement achievement = Achievement.builder()
                .name(requestDto.name())
                .rewardType(requestDto.rewardType())
                .distance(requestDto.distance())
                .cadence(requestDto.cadence())
                .week(requestDto.week())
                .cash(requestDto.cash())
                .tier(requestDto.tier())
                .background(normalizedBackground)
                .build();

        return achievementRepository.save(achievement).getId();
    }

    @Transactional
    public void updateAchievement(Long id, UpdateAchievementRequestDto requestDto) {
        Achievement achievement = achievementRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ACHIEVEMENT_NOT_FOUND));

        if (requestDto.name() != null && !requestDto.name().isEmpty()) {
            achievement.updateName(requestDto.name());
        }

        if (requestDto.rewardType() != null && requestDto.rewardType() != achievement.getRewardType()) {
            throw new BusinessException(ErrorCode.INVALID_SEARCH_CRITERIA, "Reward type cannot be changed.");
        }

        if (requestDto.rewardType() != null) {
            if (requestDto.rewardType() == RewardType.DISTANCE ||
                    requestDto.rewardType() == RewardType.CADENCE ||
                    requestDto.rewardType() == RewardType.WEEK) {
                if (requestDto.cash() != null && requestDto.cash() != 0) {
                    achievement.updateCash(requestDto.cash());
                }
            }
            else if(requestDto.rewardType() == RewardType.TIER){
                String normalizedBackground = makeStringToLowerCase(requestDto.background());
                if (requestDto.background() != null && !requestDto.background().isEmpty()) {
                    achievement.updateBackground(normalizedBackground);
                }

            }
            else{
                throw new BusinessException(ErrorCode.INVALID_SEARCH_CRITERIA, "Invalid RewardType.");
            }
        }
    }
    public Slice<AchievementDto> findAllAchievement(Pageable pageable) {
        return achievementRepository.findAll(pageable)
                .map(AchievementDto::of);
    }
    public Slice<UserAchievementDto> findUserAchievement(Long userId, Pageable pageable) {
        return userAchievementRepository.findAllSliceByUserId(userId, pageable)
                .map(UserAchievementDto::of);
    }

    @Transactional
    public Long checkAndAssignAchievement(AssignAchievementRequestDto requestDto) {
        User user = userRepository.findById(requestDto.userId()).orElseThrow(() ->
                new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        Achievement achievement = achievementRepository.findById(requestDto.achievementId())
                .orElseThrow(() -> new BusinessException(ErrorCode.ACHIEVEMENT_NOT_FOUND));

        // 이미 업적이 할당된 경우 예외 발생
        if (userAchievementRepository.existsByUserIdAndAchievementId(user.getId(), achievement.getId())) {
            throw new BusinessException(ErrorCode.USER_ACHIEVEMENT_ALREADY_CLAIMED);
        }
        // 조건 평가
        if (!evaluateCondition(user, achievement)) {
            throw new BusinessException(ErrorCode.ACHIEVEMENT_CONDITION_NOT_MET);
        }

        // 업적 할당
        UserAchievement userAchievement = assignAchievementToUser(user, achievement);
        return userAchievement.getId();
    }

    @Transactional
    public Long claimReward(RewardAchievementRequestDto requestDto) {
        UserAchievement userAchievement = userAchievementRepository.findByUserIdAndAchievementId(
                        requestDto.userId(), requestDto.achievementId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_ACHIEVEMENT_NOT_FOUND));

        if (userAchievement.isRewardClaimed()) {
            throw new BusinessException(ErrorCode.REWARD_ALREADY_CLAIMED);
        }

        Achievement achievement = userAchievement.getAchievement();
        User user = userAchievement.getUser();

        // 보상 지급
        giveRewardToUser(achievement, user);

        userAchievement.markRewardAsClaimed(); // 보상을 수령했음을 표시
        return userAchievement.getId();
    }

    public Achievement findOneAchievement(Long achievementId){
        return achievementRepository.findById(achievementId).orElseThrow(
                () -> new BusinessException(ErrorCode.ACHIEVEMENT_NOT_FOUND)
        );
    }
    //단순 조회용
    public UserAchievement findOneUserAchievement(Long userAchievementId){
        return userAchievementRepository.findById(userAchievementId).orElseThrow(
                () -> new BusinessException(ErrorCode.USER_ACHIEVEMENT_NOT_FOUND)
        );
    }


    /**
     * 업적 조건 평가
     */
    private boolean evaluateCondition(User user, Achievement achievement) {
        if (achievement.getDistance() != null) {
            double totalDistance = runningRecordRepository.findTotalDistanceByUserId(user.getId());
            return totalDistance >= achievement.getDistance();
        }

        if (achievement.getCadence() != null) {
            double averageCadence = runningRecordRepository.findAverageCadenceByUserId(user.getId());
            return averageCadence >= achievement.getCadence();
        }

        if (achievement.getWeek() != null) {
            long weeklyRuns = getWeeklyRuns(user.getId());
            return weeklyRuns >= achievement.getWeek();
        }

        if (achievement.getTier() != null) {
            return user.getTier().equals(achievement.getTier());
        }

        return false;
    }

    private long getWeeklyRuns(Long userId) {
        LocalDateTime startOfWeek = LocalDateTime.now().minusDays(7);
        return runningRecordRepository.countRecordsByUserIdAndDateRange(userId, startOfWeek, LocalDateTime.now());
    }

    private void giveRewardToUser(Achievement achievement, User user) {
        if(achievement.getRewardType() == RewardType.DISTANCE ||
                achievement.getRewardType() == RewardType.CADENCE ||
                achievement.getRewardType() == RewardType.WEEK) {
            user.addCash(achievement.getCash());
        }
        else if(achievement.getRewardType() == RewardType.TIER) {
            user.updateBackground(achievement.getBackground());
        }
        else{
            throw new BusinessException(ErrorCode.INVALID_SEARCH_CRITERIA, "Invalid RewardType.");
        }
    }

    private UserAchievement assignAchievementToUser(User user, Achievement achievement) {
        UserAchievement userAchievement = UserAchievement.builder()
                .user(user)
                .achievement(achievement)
                .rewardClaimed(false)
                .build();

        return userAchievementRepository.save(userAchievement);
    }

    //추가
    private String makeStringToLowerCase(String bg) {
        return bg != null
                ? bg.toLowerCase()
                : null;
    }
    //






}