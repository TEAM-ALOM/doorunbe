package com.alom.dorundorunbe.domain.achievement.controller;

import com.alom.dorundorunbe.domain.achievement.dto.assign.AssignAchievementRequestDto;
import com.alom.dorundorunbe.domain.achievement.dto.assign.AssignAchievementResponseDto;
import com.alom.dorundorunbe.domain.achievement.dto.create.CreateAchievementRequestDto;
import com.alom.dorundorunbe.domain.achievement.dto.create.CreateAchievementResponseDto;
import com.alom.dorundorunbe.domain.achievement.dto.query.AchievementDto;
import com.alom.dorundorunbe.domain.achievement.dto.query.UserAchievementDto;
import com.alom.dorundorunbe.domain.achievement.dto.reward.RewardAchievementRequestDto;
import com.alom.dorundorunbe.domain.achievement.dto.reward.RewardAchievementResponseDto;
import com.alom.dorundorunbe.domain.achievement.dto.update.UpdateAchievementRequestDto;
import com.alom.dorundorunbe.domain.achievement.dto.update.UpdateAchievementResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface AchievementControllerDocs {

    @Operation(
            summary = "업적 생성",
            description = """
                **업적 생성**
                새로운 업적을 생성합니다.
                
                **입력 파라미터:**
                - `name` : 업적 이름
                - `rewardType` : 보상 유형 (DISTANCE, CADENCE, WEEK, TIER)
                - `distance` : 거리 (DISTANCE 보상일 경우)
                - `cadence` : 케이던스 (CADENCE 보상일 경우)
                - `week` : 주간 목표 (WEEK 보상일 경우)
                - `cash` : 보상 금액
                - `tier` : 업적 대상 티어 (TIER 보상일 경우)
                - `background` : 티어에 맞는 테두리 (옵션)
                
                **반환값:**
                - `CreateAchievementResponseDto` : 생성된 업적 정보
                """
    )
    ResponseEntity<CreateAchievementResponseDto> createAchievement(@RequestBody CreateAchievementRequestDto requestDto);

    @Operation(
            summary = "업적 수정",
            description = """
                **업적 수정**
                특정 ID의 업적 정보를 수정합니다.
                
                **입력 파라미터:**
                - `id` : 수정할 업적 ID
                - `name` : 새 이름 (옵션)
                - `rewardType` : 새 보상 유형 (옵션)
                - `cash` : 새 보상 금액 (옵션)
                - `background` : 티어에 맞는 테두리 (옵션)
                
                **반환값:**
                - `UpdateAchievementResponseDto` : 수정된 업적 정보
                """
    )
    ResponseEntity<UpdateAchievementResponseDto> updateAchievement(
            @PathVariable Long id,
            @RequestBody UpdateAchievementRequestDto requestDto);

    @Operation(
            summary = "업적 전체 조회",
            description = """
                **업적 전체 조회**
                페이징 처리된 업적 정보를 반환합니다.
                
                **입력 파라미터:**
                - `page` : 페이지 번호
                - `size` : 페이지당 항목 수
                
                **반환값:**
                - `Slice<AchievementDto>` : 업적 정보 목록
                """
    )
    ResponseEntity<Slice<AchievementDto>> fetchAllAchievements(@RequestParam Pageable pageable);

    @Operation(
            summary = "사용자 업적 조회",
            description = """
                **사용자 업적 조회**
                특정 사용자 ID의 업적 정보를 반환합니다.
                
                **입력 파라미터:**
                - `userId` : 사용자 ID
                - `page` : 페이지 번호
                - `size` : 페이지당 항목 수
                
                **반환값:**
                - `Slice<UserAchievementDto>` : 사용자 업적 정보 목록
                """
    )
    ResponseEntity<Slice<UserAchievementDto>> fetchUserAchievements(@PathVariable Long userId, @RequestParam Pageable pageable);

    @Operation(
            summary = "업적 할당",
            description = """
                **업적 할당**
                사용자가 업적을 달성했는지 확인하고 할당합니다.
                
                **입력 파라미터:**
                - `userId` : 사용자 ID
                - `achievementId` : 업적 ID
                
                **반환값:**
                - `AssignAchievementResponseDto` : 할당된 업적 정보
                """
    )
    ResponseEntity<AssignAchievementResponseDto> assignAchievement(@RequestBody AssignAchievementRequestDto requestDto);

    @Operation(
            summary = "보상 수령",
            description = """
                **보상 수령**
                할당된 업적의 보상을 수령합니다.
                
                **입력 파라미터:**
                - `userId` : 사용자 ID
                - `achievementId` : 업적 ID
                
                **반환값:**
                - `RewardAchievementResponseDto` : 보상 수령 정보
                """
    )
    ResponseEntity<RewardAchievementResponseDto> claimReward(@RequestBody RewardAchievementRequestDto requestDto);
}
