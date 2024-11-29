package com.alom.dorundorunbe.domain.doodle.service;

import com.alom.dorundorunbe.domain.doodle.domain.UserDoodleStatus;
import com.alom.dorundorunbe.domain.doodle.dto.DoodleRequestDto;
import com.alom.dorundorunbe.domain.doodle.dto.DoodleResponseDto;
import com.alom.dorundorunbe.domain.doodle.dto.UserDoodleDto;
import com.alom.dorundorunbe.domain.doodle.dto.UserDoodleRole;
import com.alom.dorundorunbe.domain.doodle.repository.UserDoodleRepository;
import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.domain.user.repository.UserRepository;
import com.alom.dorundorunbe.domain.doodle.domain.UserDoodle;
import com.alom.dorundorunbe.domain.doodle.domain.Doodle;
import com.alom.dorundorunbe.domain.doodle.repository.DoodleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoodleService {

    @Autowired
    private DoodleRepository doodleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDoodleRepository userDoodleRepository;

    @Transactional
    public DoodleResponseDto createDoodle(DoodleRequestDto doodleRequestDto, Long userId) { //doodle 생성 기능
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("USER NOT FOUND"));

        //Doodle 생성
        Doodle doodle = Doodle.builder()
                .name(doodleRequestDto.getName())
                .goalDistance(doodleRequestDto.getGoalDistance())
                .goalCadence(doodleRequestDto.getGoalCadence())
                .goalPace(doodleRequestDto.getGoalPace())
                .goalParticipationCount(doodleRequestDto.getGoalParticipationCount())
                .password(doodleRequestDto.getPassword())
                .maxParticipant(doodleRequestDto.getMaxParticipant())
                .participants(new ArrayList<>())
                .build();
        Doodle savedDoodle = doodleRepository.save(doodle);

        UserDoodle userDoodle = new UserDoodle();
        userDoodle.setStatus(UserDoodleStatus.PARTICIPATING);
        userDoodle.setRole(UserDoodleRole.CREATOR);
        userDoodle.setJoinDate(LocalDate.now());
        userDoodle.setUser(user);
        userDoodle.setDoodle(savedDoodle);
        userDoodleRepository.save(userDoodle);

        savedDoodle.getParticipants().add(userDoodle);

        return DoodleResponseDto.from(savedDoodle);
    }

    public List<DoodleResponseDto> getAllDoodles(){ //doodle 전체 조회
        List<Doodle> doodles = doodleRepository.findAll();
        return doodles.stream()
                //map연산자로 각 Doodle 객체를 DoodleResponseDto로 변환
                .map(DoodleResponseDto::from)
                .collect(Collectors.toList());
    }

    public DoodleResponseDto getDoodleById(Long doodleId) { //doodle 상세 조회
        Optional<Doodle> doodle =  doodleRepository.findById(doodleId);
        return doodle.map(DoodleResponseDto::from)
                .orElseThrow(()->new IllegalArgumentException("NOT FOUND"));
    }

    public void deleteDoodle(Long doodleId){
        Doodle doodle = doodleRepository.findById(doodleId)
                .orElseThrow(()->new IllegalArgumentException("NOT FOUND"));

        doodleRepository.delete(doodle);
    }

    public DoodleResponseDto updateDoodle(Long doodleId, DoodleRequestDto doodleRequestDto) {
        Doodle doodle = doodleRepository.findById(doodleId)
                .orElseThrow(()->new IllegalArgumentException("NOT FOUND"));
        doodle.setName(doodleRequestDto.getName());
        doodle.setGoalDistance(doodleRequestDto.getGoalDistance());
        doodle.setGoalCadence(doodleRequestDto.getGoalCadence());
        doodle.setMaxParticipant(doodleRequestDto.getMaxParticipant());
        doodle.setGoalPace(doodleRequestDto.getGoalPace());
        doodle.setPassword(doodleRequestDto.getPassword());
        doodle.setGoalParticipationCount(doodleRequestDto.getGoalParticipationCount());

        Doodle updatedDoodle = doodleRepository.save(doodle);
        return DoodleResponseDto.from(updatedDoodle);
    }

    public DoodleResponseDto addParticipantToDoodle(Long doodleId, Long userId) { //참가자 추가
        Doodle doodle = doodleRepository.findById(doodleId).orElseThrow(()->new RuntimeException("NOT FOUND"));
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("NOT FOUND"));
        //Doodle과 user 관계추가
        UserDoodle userDoodle = new UserDoodle();
        userDoodle.setDoodle(doodle);
        userDoodle.setUser(user);
        userDoodle.setStatus(UserDoodleStatus.PARTICIPATING);
        userDoodle.setJoinDate(LocalDate.now());
        //userDoodle 저장
        userDoodleRepository.save(userDoodle);
        //참가자 리스트에 추가
        List<UserDoodle> participants = new ArrayList<>(doodle.getParticipants());
        participants.add(userDoodle);
        doodle.setParticipants(participants);

        doodleRepository.save(doodle);
        return DoodleResponseDto.from(doodle);
    }

    public DoodleResponseDto deleteParticipant(Long doodleId, Long userId){
        Doodle doodle = doodleRepository.findById(doodleId).
                orElseThrow(()->new RuntimeException("NOT FOUND"));
        User user = userRepository.findById(userId).
                orElseThrow(()->new RuntimeException("NOT FOUND"));
        UserDoodle userDoodle = userDoodleRepository.findByDoodleIdAndUserId(doodle, user).
                orElseThrow(()->new RuntimeException("유저가 해당 Doodle에 존재하지 않습니다."));

        userDoodleRepository.delete(userDoodle);
        doodle.getParticipants().remove(userDoodle);
        doodleRepository.save(doodle);
        return DoodleResponseDto.from(doodle);
   }

   public List<UserDoodleDto> getParticipants(Long doodleId){
        Doodle doodle = doodleRepository.findById(doodleId)
                .orElseThrow(()->new IllegalArgumentException("NOT FOUND"));

        return doodle.getParticipants().stream()
                .map(UserDoodleDto::from)
                .collect(Collectors.toList());
   }
   //참가자 doodle 완료 상태 업데이트 로직 구현
    public UserDoodleDto updateParticipantStatus(Long doodleId, Long userId, UserDoodleStatus status){
        Doodle doodle = doodleRepository.findById(doodleId).
                orElseThrow(()->new RuntimeException("NOT FOUND"));
        User user = userRepository.findById(userId).
                orElseThrow(()->new RuntimeException("NOT FOUND"));
        UserDoodle userDoodle = userDoodleRepository.findByDoodleIdAndUserId(doodle, user)
                .orElseThrow(()->new IllegalArgumentException("NOT FOUND"));
        userDoodle.setStatus(status);
        userDoodleRepository.save(userDoodle);
        return UserDoodleDto.from(userDoodle);
    }
}
