package com.alom.dorundorunbe.domain.achievement.service;
import com.alom.dorundorunbe.domain.RunningRecord.repository.RunningRecordRepository;
import com.alom.dorundorunbe.domain.achievement.domain.Achievement;
import com.alom.dorundorunbe.domain.achievement.domain.RewardType;
import com.alom.dorundorunbe.domain.achievement.dto.create.CreateAchievementRequestDto;
import com.alom.dorundorunbe.domain.achievement.exception.AchievementAlreadyExistsException;
import com.alom.dorundorunbe.domain.achievement.repository.AchievementRepository;
import com.alom.dorundorunbe.domain.achievement.repository.UserAchievementRepository;
import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.domain.user.repository.UserRepository;
import com.alom.dorundorunbe.global.enums.Tier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AchievementServiceTest {
    @InjectMocks
    private AchievementService achievementService;

    @Mock
    private AchievementRepository achievementRepository;

    @Mock
    private UserAchievementRepository userAchievementRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RunningRecordRepository runningRecordRepository;

    private Achievement sampleAchievement;
    private User sampleUser;

    @BeforeEach
    void setUp() {
        sampleAchievement = Achievement.builder()
                .id(1L)
                .name("Test Achievement")
                .rewardType(RewardType.DISTANCE)
                .distance(100)
                .cash(1000L)
                .background("original_background")
                .build();

        sampleUser = User.builder()
                .id(1L)
                .name("Test User")
                .cash(0L)
                .tier(Tier.BEGINNER)
                .build();
    }
    @Test
    @DisplayName("업적 생성 - 성공")
    void createAchievement_success() {
        CreateAchievementRequestDto requestDto = new CreateAchievementRequestDto(
                "Test Achievement",
                RewardType.DISTANCE,
                100,
                null,
                null,
                1000L,
                null,
                null
        );

        when(achievementRepository.findByName(requestDto.name())).thenReturn(Optional.empty());
        when(achievementRepository.save(any(Achievement.class))).thenReturn(sampleAchievement);

        Long achievementId = achievementService.createAchievement(requestDto);

        assertThat(achievementId).isEqualTo(sampleAchievement.getId());
        verify(achievementRepository, times(1)).save(any(Achievement.class));
    }

    @Test
    @DisplayName("업적 생성 - 중복 예외 발생")
    void createAchievement_fail_duplicate() {
        CreateAchievementRequestDto requestDto = new CreateAchievementRequestDto(
                "Test Achievement",
                RewardType.DISTANCE,
                100,
                null,
                null,
                1000L,
                null,
                null
        );

        when(achievementRepository.findByName(requestDto.name())).thenReturn(Optional.of(sampleAchievement));


        assertThatThrownBy(()->achievementService.createAchievement(requestDto))
                .isInstanceOf(AchievementAlreadyExistsException.class);

        verify(achievementRepository, never()).save(any(Achievement.class));
    }

}