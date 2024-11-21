package com.alom.dorundorunbe.domain.challenge.controller;

import com.alom.dorundorunbe.domain.challenge.dto.ChallengeResponseDto;
import com.alom.dorundorunbe.domain.challenge.service.UserChallengeService;
import com.alom.dorundorunbe.domain.user.domain.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/challenges")
@RequiredArgsConstructor
@Tag(
    name = "챌런지 유저관리 API"
)
public class UserChallengeController implements UserChallengeControllerDocs{

  private final UserChallengeService userChallengeService;

  @PostMapping("/{challengeId}/join")
  public ResponseEntity<String> joinChallenge(@PathVariable Long challengeId, @RequestParam Long userId) {
    userChallengeService.joinChallenge(userId, challengeId);
    return ResponseEntity.ok("User joined the challenge successfully");
  }

  @DeleteMapping("/{challengeId}/leave")
  public ResponseEntity<String> leaveChallenge(@PathVariable Long challengeId, @RequestParam Long userId) {
    userChallengeService.leaveChallenge(userId, challengeId);
    return ResponseEntity.ok("User left the challenge successfully");
  }

  @GetMapping("/{challengeId}/participants")
  public ResponseEntity<List<User>> getChallengeParticipants(@PathVariable Long challengeId) {
    List<User> participants = userChallengeService.getChallengeParticipants(challengeId);
    return ResponseEntity.ok(participants);
  }

  @GetMapping("/users/{userId}/challenges")
  public ResponseEntity<List<ChallengeResponseDto>> getUserChallenges(@PathVariable Long userId) {
    List<ChallengeResponseDto> userChallenges = userChallengeService.getUserChallenges(userId);
    return ResponseEntity.ok(userChallenges);
  }
}
