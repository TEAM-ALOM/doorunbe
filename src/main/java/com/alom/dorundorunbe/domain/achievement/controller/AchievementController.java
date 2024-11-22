package com.alom.dorundorunbe.domain.achievement.controller;

import com.alom.dorundorunbe.domain.achievement.domain.Achievement;
import com.alom.dorundorunbe.domain.achievement.domain.UserAchievement;
import com.alom.dorundorunbe.domain.achievement.dto.assign.AssignAchievementRequestDto;
import com.alom.dorundorunbe.domain.achievement.dto.assign.AssignAchievementResponseDto;
import com.alom.dorundorunbe.domain.achievement.dto.create.CreateAchievementRequestDto;
import com.alom.dorundorunbe.domain.achievement.dto.create.CreateAchievementResponseDto;
import com.alom.dorundorunbe.domain.achievement.dto.query.AchievementDto;
import com.alom.dorundorunbe.domain.achievement.dto.reward.RewardAchievementRequestDto;
import com.alom.dorundorunbe.domain.achievement.dto.reward.RewardAchievementResponseDto;
import com.alom.dorundorunbe.domain.achievement.dto.update.UpdateAchievementRequestDto;
import com.alom.dorundorunbe.domain.achievement.dto.update.UpdateAchievementResponseDto;
import com.alom.dorundorunbe.domain.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/achievement")
public class AchievementController {

    private final AchievementService achievementService;

    @PostMapping
    public ResponseEntity<CreateAchievementResponseDto> createAchievement(@RequestBody CreateAchievementRequestDto requestDto) {
        Long id = achievementService.createAchievement(requestDto.name(), requestDto.rewardType(), requestDto.rewardValue(), requestDto.condition());
        Achievement achievement = achievementService.findOneAchievement(id);
        return ResponseEntity.ok(CreateAchievementResponseDto.of(achievement));

    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateAchievementResponseDto> updateAchievement(
            @PathVariable Long id,
            @RequestBody UpdateAchievementRequestDto requestDto) {

        achievementService.updateAchievement(id, requestDto.name(), requestDto.rewardValue());
        Achievement updatedAchievement = achievementService.findOneAchievement(id);
        return ResponseEntity.ok(UpdateAchievementResponseDto.of(updatedAchievement));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Slice<AchievementDto>> fetchUserAchievements(@PathVariable Long userId,
                                                                     @PageableDefault(size = 10) Pageable pageable) {

        Slice<AchievementDto> achievementDtosSlice = achievementService.fetchAllUserAchievements(userId, pageable);
        return ResponseEntity.ok(achievementDtosSlice);

    }

    @PostMapping("/assign")
    public ResponseEntity<AssignAchievementResponseDto> assignAchievement(@RequestBody AssignAchievementRequestDto requestDto) {
        Long id = achievementService.checkAndAssignAchievement(requestDto.userId(), requestDto.achievementId());
        UserAchievement userAchievement = achievementService.findOneUserAchievement(id);
        return ResponseEntity.ok(AssignAchievementResponseDto.of(userAchievement));
    }

    @PostMapping("/reward")
    public ResponseEntity<RewardAchievementResponseDto> claimReward(@RequestBody RewardAchievementRequestDto requestDto) {
        Long id = achievementService.claimReward(requestDto.userId(), requestDto.achievementId());
        UserAchievement userAchievement = achievementService.findOneUserAchievement(id);
        return ResponseEntity.ok(RewardAchievementResponseDto.of(userAchievement));
    }
}

