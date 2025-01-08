package com.alom.dorundorunbe.domain.achievement.service;
import com.alom.dorundorunbe.domain.RunningRecord.repository.RunningRecordRepository;
import com.alom.dorundorunbe.domain.achievement.domain.Achievement;
import com.alom.dorundorunbe.domain.achievement.domain.RewardType;
import com.alom.dorundorunbe.domain.achievement.domain.UserAchievement;
import com.alom.dorundorunbe.domain.achievement.dto.assign.AssignAchievementRequestDto;
import com.alom.dorundorunbe.domain.achievement.dto.create.CreateAchievementRequestDto;
import com.alom.dorundorunbe.domain.achievement.dto.reward.RewardAchievementRequestDto;
import com.alom.dorundorunbe.domain.achievement.dto.update.UpdateAchievementRequestDto;
import com.alom.dorundorunbe.domain.achievement.exception.*;
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

import java.util.List;
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

    @Test
    @DisplayName("업적 업데이트 - 성공 (이름 변경)")
    void updateAchievement_success_nameChange() {
        UpdateAchievementRequestDto requestDto = new UpdateAchievementRequestDto("Updated Name", null, null, null);

        when(achievementRepository.findById(1L)).thenReturn(Optional.of(sampleAchievement));

        achievementService.updateAchievement(1L, requestDto);

        assertThat(sampleAchievement.getName()).isEqualTo("Updated Name");
    }

    @Test
    @DisplayName("업적 업데이트 - 성공 (보상 금액 변경)")
    void updateAchievement_success_cashChange() {
        UpdateAchievementRequestDto requestDto = new UpdateAchievementRequestDto(null, RewardType.DISTANCE, 2000L, null);

        when(achievementRepository.findById(1L)).thenReturn(Optional.of(sampleAchievement));

        achievementService.updateAchievement(1L, requestDto);

        assertThat(sampleAchievement.getCash()).isEqualTo(2000L);
    }

    @Test
    @DisplayName("업적 업데이트 - 성공 (배경 변경)")
    void updateAchievement_success_backgroundChange() {
        Achievement bgAchievement = Achievement.builder()
                .id(2L)
                .name("Original Achievement")
                .rewardType(RewardType.TIER)
                .background("original_background")
                .build();


        UpdateAchievementRequestDto requestDto = new UpdateAchievementRequestDto(null, RewardType.TIER, null, "new_background");

        when(achievementRepository.findById(2L)).thenReturn(Optional.of(bgAchievement));

        achievementService.updateAchievement(2L, requestDto);

        assertThat(bgAchievement.getBackground()).isEqualTo("new_background");
    }

    @Test
    @DisplayName("업적 업데이트 - 존재하지 않는 경우 예외 발생")
    void updateAchievement_fail_notFound() {
        UpdateAchievementRequestDto requestDto = new UpdateAchievementRequestDto("Updated Name", RewardType.DISTANCE, 1000L, null);

        when(achievementRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> achievementService.updateAchievement(1L, requestDto))
                .isInstanceOf(AchievementNotFoundException.class);
    }

    @Test
    @DisplayName("업적 업데이트 - RewardType 에 따른 보상 값 잘못 기입")
    void updateAchievement_fail_invalidRewardType() {
        UpdateAchievementRequestDto requestDto = new UpdateAchievementRequestDto(null, RewardType.TIER, 500L, null);

        when(achievementRepository.findById(1L)).thenReturn(Optional.of(sampleAchievement));

        assertThatThrownBy(() -> achievementService.updateAchievement(1L, requestDto))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("업적 업데이트 - 이름이 null 또는 빈 문자열이면 업데이트되지 않음")
    void updateAchievement_fail_emptyName() {
        UpdateAchievementRequestDto requestDto = new UpdateAchievementRequestDto("", RewardType.DISTANCE, null, null);

        when(achievementRepository.findById(1L)).thenReturn(Optional.of(sampleAchievement));

        achievementService.updateAchievement(1L, requestDto);

        assertThat(sampleAchievement.getName()).isEqualTo("Test Achievement");
    }

    @Test
    @DisplayName("업적 업데이트 - 보상 금액이 null 이면 업데이트되지 않음")
    void updateAchievement_fail_nullCash() {
        UpdateAchievementRequestDto requestDto = new UpdateAchievementRequestDto(null, RewardType.DISTANCE, null, null);

        when(achievementRepository.findById(1L)).thenReturn(Optional.of(sampleAchievement));

        achievementService.updateAchievement(1L, requestDto);

        assertThat(sampleAchievement.getCash()).isEqualTo(1000L);
    }

    @Test
    @DisplayName("업적 업데이트 - 배경이 null 또는 빈 문자열이면 업데이트되지 않음")
    void updateAchievement_fail_emptyBackground() {
        Achievement bgAchievement = Achievement.builder()
                .id(2L)
                .name("Original Achievement")
                .rewardType(RewardType.TIER)
                .background("original_background")
                .build();

        UpdateAchievementRequestDto requestDto = new UpdateAchievementRequestDto(null, RewardType.TIER, null, "");

        when(achievementRepository.findById(2L)).thenReturn(Optional.of(bgAchievement));

        achievementService.updateAchievement(2L, requestDto);

        assertThat(sampleAchievement.getBackground()).isEqualTo("original_background");
    }

    @Test
    @DisplayName("업적 조회 - 성공")
    void findOneAchievement_success() {
        when(achievementRepository.findById(1L)).thenReturn(Optional.of(sampleAchievement));

        Achievement foundAchievement = achievementService.findOneAchievement(1L);

        assertThat(foundAchievement).isEqualTo(sampleAchievement);
    }

    @Test
    @DisplayName("업적 조회 - 존재하지 않는 경우 예외 발생")
    void findOneAchievement_fail_notFound() {
        when(achievementRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AchievementNotFoundException.class, () -> {
            achievementService.findOneAchievement(1L);
        });
    }

    @Test
    @DisplayName("사용자 업적 조회 - 성공")
    void findUserAchievements_success() {
        UserAchievement userAchievement = UserAchievement.builder()
                .user(sampleUser)
                .achievement(sampleAchievement)
                .rewardClaimed(false)
                .build();

        when(userAchievementRepository.findAllSliceByUserId(eq(1L), any(PageRequest.class)))
                .thenReturn(new SliceImpl<>(List.of(userAchievement)));

        var result = achievementService.findUserAchievement(1L, PageRequest.of(0, 10));

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).id()).isEqualTo(sampleAchievement.getId());
    }

    @Test
    @DisplayName("보상 수령 - 성공(cash 관련)")
    void claimReward_success() {

        UserAchievement userAchievement = UserAchievement.builder()
                .id(1L)
                .user(sampleUser)
                .achievement(sampleAchievement)
                .rewardClaimed(false)
                .build();


        when(userAchievementRepository.findByUserIdAndAchievementId(1L, 1L))
                .thenReturn(Optional.of(userAchievement));

        Long achievementId = achievementService.claimReward(new RewardAchievementRequestDto(1L, 1L));

        assertThat(achievementId).isEqualTo(userAchievement.getAchievement().getId());
        assertThat(userAchievement.isRewardClaimed()).isTrue();
        assertThat(sampleUser.getCash()).isEqualTo(1000L);//캐시 증가 여부를 확인
    }

    @Test
    @DisplayName("보상 수령 - TIER 업적 배경 변경 성공")
    void claimReward_tierReward_success() {

        Achievement tierAchievement = Achievement.builder()
                .id(2L)
                .name("Tier Achievement")
                .rewardType(RewardType.TIER)
                .tier(Tier.BEGINNER) //동일 tier
                .background("gold")
                .build();




        UserAchievement userAchievement = UserAchievement.builder()
                .id(2L)
                .user(sampleUser)
                .achievement(tierAchievement)
                .rewardClaimed(false)
                .build();

        when(userAchievementRepository.findByUserIdAndAchievementId(1L, 2L))
                .thenReturn(Optional.of(userAchievement));

        Long achievementId = achievementService.claimReward(new RewardAchievementRequestDto(1L, 2L));


        assertThat(achievementId).isEqualTo(2L);
        assertThat(sampleUser.getBackground()).isEqualTo("gold");
    }

    @Test
    @DisplayName("업적 할당 - 이미 할당된 업적인 경우 예외 발생")
    void checkAndAssignAchievement_fail_alreadyAssigned() {
        when(achievementRepository.findById(1L))
                .thenReturn(Optional.of(sampleAchievement));

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(sampleUser));


        when(userAchievementRepository.existsByUserIdAndAchievementId(1L, 1L))
                .thenReturn(true);

        assertThrows(UserAchievementAlreadyClaimedException.class, () -> {
            achievementService.checkAndAssignAchievement(new AssignAchievementRequestDto(1L, 1L));
        });


        verify(userAchievementRepository, never()).save(any(UserAchievement.class));
    }

    @Test
    @DisplayName("업적 할당 - TIER 업적이지만 사용자 Tier 불일치 예외 발생")
    void checkAndAssignAchievement_fail_wrongTier() {
        Achievement tierAchievement = Achievement.builder()
                .id(2L)
                .name("Tier Achievement")
                .rewardType(RewardType.TIER)
                .tier(Tier.PRO) //업적의 Tier = PRO
                .background("gold")
                .build();

        when(achievementRepository.findById(2L))
                .thenReturn(Optional.of(tierAchievement));

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(sampleUser)); //user 의 Tier = BEGINNER


        assertThrows(AchievementConditionNotMetException.class, () -> {
            achievementService.checkAndAssignAchievement(new AssignAchievementRequestDto(1L, 2L));
        });


        verify(userAchievementRepository, never()).save(any(UserAchievement.class));
    }

    @Test
    @DisplayName("보상 수령 - 이미 받은 보상 예외 발생")
    void claimReward_fail_alreadyClaimed() {
        UserAchievement userAchievement = UserAchievement.builder()
                .user(sampleUser)
                .achievement(sampleAchievement)
                .rewardClaimed(true)
                .build();

        when(userAchievementRepository.findByUserIdAndAchievementId(1L, 1L))
                .thenReturn(Optional.of(userAchievement));

        assertThrows(RewardAlreadyClaimedException.class, () -> {
            achievementService.claimReward(new RewardAchievementRequestDto(1L, 1L));
        });
    }

    @Test
    @DisplayName("보상 수령 - 존재하지 않는 업적 예외 발생")
    void claimReward_fail_notFound() {
        when(userAchievementRepository.findByUserIdAndAchievementId(1L, 1L))
                .thenReturn(Optional.empty());

        assertThrows(UserAchievementNotFoundException.class, () -> {
            achievementService.claimReward(new RewardAchievementRequestDto(1L, 1L));
        });
    }

    @Test
    @DisplayName("업적 할당 - 거리 조건 미충족 예외 발생")
    void checkAndAssignAchievement_fail_conditionNotMet() {

        when(achievementRepository.findById(1L))
                .thenReturn(Optional.of(sampleAchievement));


        when(userRepository.findById(1L))
                .thenReturn(Optional.of(sampleUser));


        when(runningRecordRepository.findTotalDistanceByUserId(1L))
                .thenReturn(50.0);

        when(userAchievementRepository.existsByUserIdAndAchievementId(1L, 1L))
                .thenReturn(false);


        assertThrows(AchievementConditionNotMetException.class, () -> {
            achievementService.checkAndAssignAchievement(new AssignAchievementRequestDto(1L, 1L));
        });


        verify(userAchievementRepository, never()).save(any(UserAchievement.class));
    }










}