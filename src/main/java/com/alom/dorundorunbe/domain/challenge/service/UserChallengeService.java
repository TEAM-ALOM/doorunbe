package com.alom.dorundorunbe.domain.challenge.service;

import com.alom.dorundorunbe.domain.challenge.domain.Challenge;
import com.alom.dorundorunbe.domain.challenge.domain.UserChallenge;
import com.alom.dorundorunbe.domain.challenge.dto.ChallengeResponseDto;
import com.alom.dorundorunbe.domain.challenge.repository.ChallengeRepository;
import com.alom.dorundorunbe.domain.challenge.repository.UserChallengeRepository;
import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserChallengeService {

  private final UserChallengeRepository userChallengeRepository;
  private final UserRepository userRepository;
  private final ChallengeRepository challengeRepository;

  public void joinChallenge(Long userId, Long challengeId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NoSuchElementException("User not found with id: " + userId));
    Challenge challenge = challengeRepository.findById(challengeId)
        .orElseThrow(() -> new NoSuchElementException("Challenge not found with id: " + challengeId));

    UserChallenge userChallenge = UserChallenge.builder()
        .user(user)
        .challenge(challenge)
        .build();

    userChallengeRepository.save(userChallenge);
  }

  public void leaveChallenge(Long userId, Long challengeId) {
    UserChallenge userChallenge = userChallengeRepository.findByUserIdAndChallengeId(userId, challengeId)
        .orElseThrow(() -> new NoSuchElementException("UserChallenge not found for userId: " + userId + " and challengeId: " + challengeId));

    userChallengeRepository.delete(userChallenge);
  }

  public List<User> getChallengeParticipants(Long challengeId) {
    List<UserChallenge> userChallenges = userChallengeRepository.findByChallengeId(challengeId);

    return userChallenges.stream()
        .map(UserChallenge::getUser)
        .collect(Collectors.toList());
  }

  public List<ChallengeResponseDto> getUserChallenges(Long userId) {
    List<UserChallenge> userChallenges = userChallengeRepository.findByUserId(userId);

    return userChallenges.stream()
        .map(userChallenge -> userChallenge.getChallenge().toResponseDto())
        .collect(Collectors.toList());
  }
}
