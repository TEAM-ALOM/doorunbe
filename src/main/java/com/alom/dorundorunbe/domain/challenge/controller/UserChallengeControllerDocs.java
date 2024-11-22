package com.alom.dorundorunbe.domain.challenge.controller;

import com.alom.dorundorunbe.domain.challenge.dto.ChallengeResponseDto;
import com.alom.dorundorunbe.domain.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface UserChallengeControllerDocs {

  @Operation(
      summary = "챌린지 참가",
      description = """
            **챌린지 참가 API**
            
            유저가 특정 챌린지에 참가합니다.
            
            **요청 파라미터:**
            
            - **`challengeId (Path Variable)`**: 참가할 챌린지의 ID
            - **`userId (Request Param)`**: 참가하는 유저의 ID
            
            **응답 값:**
            
            - **`String`**: 참가 완료 메시지
        """
  )
  ResponseEntity<String> joinChallenge(
      @PathVariable Long challengeId,
      @RequestParam Long userId
  );

  @Operation(
      summary = "챌린지 탈퇴",
      description = """
            **챌린지 탈퇴 API**
            
            유저가 특정 챌린지에서 탈퇴합니다.
            
            **요청 파라미터:**
            
            - **`challengeId (Path Variable)`**: 탈퇴할 챌린지의 ID
            - **`userId (Request Param)`**: 탈퇴하는 유저의 ID
            
            **응답 값:**
            
            - **`String`**: 탈퇴 완료 메시지
        """
  )
  ResponseEntity<String> leaveChallenge(
      @PathVariable Long challengeId,
      @RequestParam Long userId
  );

  @Operation(
      summary = "챌린지 참가자 목록 조회",
      description = """
            **챌린지 참가자 목록 조회 API**
            
            특정 챌린지에 참가한 유저들의 목록을 조회합니다.
            
            **요청 파라미터:**
            
            - **`challengeId (Path Variable)`**: 조회할 챌린지의 ID
            
            **응답 값:**
            
            - **`List<User>`**: 참가 유저 목록
        """
  )
  ResponseEntity<List<User>> getChallengeParticipants(
      @PathVariable Long challengeId
  );

  @Operation(
      summary = "유저가 참가한 챌린지 목록 조회",
      description = """
            **유저가 참가한 챌린지 목록 조회 API**
            
            특정 유저가 참가한 모든 챌린지 목록을 조회합니다.
            
            **요청 파라미터:**
            
            - **`userId (Path Variable)`**: 조회할 유저의 ID
            
            **응답 값:**
            
            - **`List<ChallengeResponseDto>`**: 참가한 챌린지 목록
        """
  )
  ResponseEntity<List<ChallengeResponseDto>> getUserChallenges(
      @PathVariable Long userId
  );
}
