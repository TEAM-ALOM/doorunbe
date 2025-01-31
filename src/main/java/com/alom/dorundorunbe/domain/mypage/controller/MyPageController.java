package com.alom.dorundorunbe.domain.mypage.controller;


import com.alom.dorundorunbe.domain.runningrecord.domain.RunningRecord;
import com.alom.dorundorunbe.domain.mypage.dto.*;
import com.alom.dorundorunbe.domain.mypage.service.MyPageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/")
    @Operation(summary = "마이페이지 조회", description = "마이페이지를 조회합니다")
    public ResponseEntity<?> myPage(){
        // 사용자 식별 - email 반환
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        List<AchievementResponse> achievementResponses = myPageService.getAchievements(username);
        String rank = myPageService.getUserRank(username);
        List<RunningRecord> runningRecords = myPageService.getRunningRecords(username);
        String nickname = myPageService.getUserNickname(username);

        MyPageResponse myPageResponse = new MyPageResponse(username, nickname, achievementResponses, rank, runningRecords);
        return new ResponseEntity<>(myPageResponse, HttpStatus.OK);

    }

    @PutMapping("/update")
    @Operation(summary = "사용자 정보 수정", description = "사용자 정보를 수정합니다")
    public ResponseEntity<String> updateUser(@RequestBody UserUpdateDTO userDTO){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return myPageService.updateByUsername(userDTO, username);
    }

    @PutMapping("/update/nickname")
    @Operation(summary = "닉네임 수정", description = "닉네임을 수정합니다")
    public ResponseEntity<Void> updateNickname(String nickname) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        myPageService.updateNickname(username, nickname);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/myPage/delete")
    @Operation(summary = "회원 탈퇴", description = "회원을 탈퇴합니다")
    public ResponseEntity<String> deleteUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return myPageService.deleteUser(username);
    }
}
