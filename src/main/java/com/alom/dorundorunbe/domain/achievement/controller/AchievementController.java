package com.alom.dorundorunbe.domain.achievement.controller;

import com.alom.dorundorunbe.domain.achievement.domain.Achievement;
import com.alom.dorundorunbe.domain.achievement.dto.create.CreateAchievementRequestDto;
import com.alom.dorundorunbe.domain.achievement.dto.create.CreateAchievementResponseDto;
import com.alom.dorundorunbe.domain.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/achievement")
public class AchievementController {
    private final AchievementService achievementService;

    /**
     * 업적 생성
     */
    @PostMapping
    public ResponseEntity<CreateAchievementResponseDto> createAchievement(@RequestBody CreateAchievementRequestDto requestDto) {
        Long id = achievementService.createAchievement(requestDto);
        Achievement achievement = achievementService.findOneAchievement(id);
        return ResponseEntity.ok(CreateAchievementResponseDto.of(achievement));
    }
}
