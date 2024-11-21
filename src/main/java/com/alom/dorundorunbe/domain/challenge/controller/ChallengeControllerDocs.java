package com.alom.dorundorunbe.domain.challenge.controller;

import com.alom.dorundorunbe.domain.challenge.dto.ChallengeRequestDto;
import com.alom.dorundorunbe.domain.challenge.dto.ChallengeResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ChallengeControllerDocs {

  @Operation(
      summary = "챌린지 생성",
      description = """
            **챌린지 생성**

            새로운 챌린지를 생성합니다.

            **입력 파라미터:**
                    
            - **`String name`**: 챌린지 이름
            - **`String description`**: 챌린지 설명
            - **`LocalDate startDate`**: 챌린지 시작 날짜
            - **`LocalDate endDate`**: 챌린지 종료 날짜
            - **`String location`**: 챌린지 위치
            - **`int distance`**: 챌린지 거리 (km)

            **반환값:**
            
            - **`ChallengeResponseDto`**: 생성된 챌린지 정보
        """
  )
  ResponseEntity<ChallengeResponseDto> createChallenge(@RequestBody ChallengeRequestDto requestDto);

  @Operation(
      summary = "챌린지 수정",
      description = """
            **챌린지 수정**

            기존의 챌린지 정보를 수정합니다.

            **입력 파라미터:**
                    
            - **`Long id`**: 수정할 챌린지의 ID
            - **`ChallengeRequestDto`**: 수정할 챌린지의 새로운 정보

            **반환값:**
            
            - **`ChallengeResponseDto`**: 수정된 챌린지 정보
        """
  )
  ResponseEntity<ChallengeResponseDto> updateChallenge(@PathVariable Long id, @RequestBody ChallengeRequestDto requestDto);

  @Operation(
      summary = "챌린지 삭제",
      description = """
            **챌린지 삭제**

            지정된 ID의 챌린지를 삭제합니다.

            **입력 파라미터:**
                    
            - **`Long id`**: 삭제할 챌린지의 ID

            **반환값:**
            
            - **`void`**: 삭제 완료 시 반환값 없음
        """
  )
  ResponseEntity<Void> deleteChallenge(@PathVariable Long id);

  @Operation(
      summary = "챌린지 상세 조회",
      description = """
            **챌린지 상세 조회**

            특정 ID를 가진 챌린지의 상세 정보를 조회합니다.

            **입력 파라미터:**
                    
            - **`Long id`**: 조회할 챌린지의 ID

            **반환값:**
            
            - **`ChallengeResponseDto`**: 조회된 챌린지의 상세 정보
        """
  )
  ResponseEntity<ChallengeResponseDto> getChallengeDetail(@PathVariable Long id);

  @Operation(
      summary = "챌린지 목록 조회",
      description = """
            **챌린지 목록 조회**

            등록된 모든 챌린지의 목록을 조회합니다.

            **반환값:**
            
            - **`List<ChallengeResponseDto>`**: 등록된 챌린지의 목록
        """
  )
  ResponseEntity<List<ChallengeResponseDto>> getAllChallenges();
}
