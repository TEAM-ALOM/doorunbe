package com.alom.dorundorunbe.domain.achievement.service;

import com.alom.dorundorunbe.domain.RunningRecord.repository.RunningRecordRepository;
import com.alom.dorundorunbe.domain.achievement.domain.Achievement;
import com.alom.dorundorunbe.domain.achievement.domain.RewardType;
import com.alom.dorundorunbe.domain.achievement.dto.create.CreateAchievementRequestDto;
import com.alom.dorundorunbe.domain.achievement.dto.update.UpdateAchievementRequestDto;
import com.alom.dorundorunbe.domain.achievement.exception.AchievementAlreadyExistsException;
import com.alom.dorundorunbe.domain.achievement.exception.AchievementNotFoundException;
import com.alom.dorundorunbe.domain.achievement.exception.common.ErrorMessages;
import com.alom.dorundorunbe.domain.achievement.repository.AchievementRepository;
import com.alom.dorundorunbe.domain.achievement.repository.UserAchievementRepository;
import com.alom.dorundorunbe.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            throw new AchievementAlreadyExistsException(
                    "already exist");
        });

        Achievement achievement = Achievement.builder()
                .name(requestDto.name())
                .rewardType(requestDto.rewardType())
                .distance(requestDto.distance())
                .cadence(requestDto.cadence())
                .week(requestDto.week())
                .cash(requestDto.cash())
                .tier(requestDto.tier())
                .background(requestDto.background())
                .build();

        return achievementRepository.save(achievement).getId();
    }

    @Transactional
    public void updateAchievement(Long id, UpdateAchievementRequestDto requestDto) {
        Achievement achievement = achievementRepository.findById(id)
                .orElseThrow(() -> new AchievementNotFoundException(
                        String.format(ErrorMessages.ACHIEVEMENT_NOT_FOUND, id)));

        if (requestDto.name() != null && !requestDto.name().isEmpty()) {
            achievement.updateName(requestDto.name());
        }
        if (requestDto.rewardType() != null) {
            if (requestDto.rewardType() == RewardType.DISTANCE ||
                    requestDto.rewardType() == RewardType.CADENCE ||
                    requestDto.rewardType() == RewardType.WEEK) {
                if (requestDto.cash() != null) {
                    achievement.updateCash(requestDto.cash());
                }
            }
            else if(requestDto.rewardType() == RewardType.TIER){
                if (requestDto.background() != null && !requestDto.background().isEmpty()) {
                    achievement.updateBackground(requestDto.background());
                }

            }
            else{
                throw new IllegalArgumentException("Invalid RewardType");
            }
        }
    }


}
