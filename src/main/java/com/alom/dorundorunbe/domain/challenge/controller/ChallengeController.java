package com.alom.dorundorunbe.domain.challenge.controller;

import com.alom.dorundorunbe.domain.challenge.dto.ChallengeRequestDto;
import com.alom.dorundorunbe.domain.challenge.dto.ChallengeResponseDto;
import com.alom.dorundorunbe.domain.challenge.service.ChallengeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/challenges")
@Tag(
    name = "챌런지 관리 API"
)
public class ChallengeController implements ChallengeControllerDocs {

  private final ChallengeService challengeService;

  @PostMapping
  public ResponseEntity<ChallengeResponseDto> createChallenge(@RequestBody ChallengeRequestDto requestDto) {
    ChallengeResponseDto responseDto = challengeService.createChallenge(requestDto);
    return ResponseEntity.ok(responseDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ChallengeResponseDto> updateChallenge(@PathVariable Long id, @RequestBody ChallengeRequestDto requestDto) {
    ChallengeResponseDto responseDto = challengeService.updateChallenge(id, requestDto);
    return ResponseEntity.ok(responseDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteChallenge(@PathVariable Long id) {
    challengeService.deleteChallenge(id);
    return ResponseEntity.noContent().build();
  }


  @GetMapping("/{id}")
  public ResponseEntity<ChallengeResponseDto> getChallengeDetail(@PathVariable Long id) {
    ChallengeResponseDto responseDto = challengeService.getChallengeDetail(id);

    return ResponseEntity.ok(responseDto);
  }

  @GetMapping
  public ResponseEntity<List<ChallengeResponseDto>> getAllChallenges() {
    List<ChallengeResponseDto> responseDtos = challengeService.getAllChallenges();
    return ResponseEntity.ok(responseDtos);
  }

}
