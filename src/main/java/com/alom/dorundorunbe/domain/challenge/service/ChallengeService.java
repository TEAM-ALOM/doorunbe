package com.alom.dorundorunbe.domain.challenge.service;


import com.alom.dorundorunbe.domain.challenge.domain.Challenge;
import com.alom.dorundorunbe.domain.challenge.dto.ChallengeRequestDto;
import com.alom.dorundorunbe.domain.challenge.dto.ChallengeResponseDto;
import com.alom.dorundorunbe.domain.challenge.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChallengeService {

  private final ChallengeRepository challengeRepository;

  public ChallengeResponseDto createChallenge(ChallengeRequestDto requestDto) {
    Challenge challenge = Challenge.builder()
        .name(requestDto.name())
        .description(requestDto.description())
        .challengeUrl(requestDto.challengeUrl())
        .startDate(requestDto.startDate())
        .endDate(requestDto.endDate())
        .location(requestDto.location())
        .distance(requestDto.distance())
        .build();

    Challenge savedChallenge = challengeRepository.save(challenge);
    return savedChallenge.toResponseDto();
  }

  public ChallengeResponseDto updateChallenge(Long id, ChallengeRequestDto requestDto) {
    Challenge challenge = challengeRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Challenge not found with id: " + id));

    challenge.update(
        requestDto.name(),
        requestDto.description(),
        requestDto.challengeUrl(),
        requestDto.startDate(),
        requestDto.endDate(),
        requestDto.location(),
        requestDto.distance()
    );

    Challenge updatedChallenge = challengeRepository.save(challenge);
    return updatedChallenge.toResponseDto();
  }

  public void deleteChallenge(Long id) {
    Challenge challenge = challengeRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Challenge not found with id: " + id));

    challengeRepository.delete(challenge);
  }

  public ChallengeResponseDto getChallengeDetail(Long id) {
    Challenge challenge = challengeRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Challenge not found with id: " + id));
    return challenge.toResponseDto();
  }

  public List<ChallengeResponseDto> getAllChallenges() {
    List<Challenge> challenges = challengeRepository.findAll();
    return challenges.stream()
        .map(Challenge::toResponseDto)
        .collect(Collectors.toList());
  }

}
