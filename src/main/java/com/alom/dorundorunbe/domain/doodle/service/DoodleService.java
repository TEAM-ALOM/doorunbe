package com.alom.dorundorunbe.domain.doodle.service;

import com.alom.dorundorunbe.domain.doodle.domain.UserDoodleStatus;
import com.alom.dorundorunbe.domain.doodle.dto.DoodleRequestDto;
import com.alom.dorundorunbe.domain.doodle.dto.DoodleResponseDto;
import com.alom.dorundorunbe.domain.doodle.dto.UserDoodleDto;
import com.alom.dorundorunbe.domain.doodle.dto.UserDoodleRole;
import com.alom.dorundorunbe.domain.doodle.repository.UserDoodleRepository;
import com.alom.dorundorunbe.domain.runningrecord.domain.RunningRecord;
import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.domain.user.repository.UserRepository;
import com.alom.dorundorunbe.domain.doodle.domain.UserDoodle;
import com.alom.dorundorunbe.domain.doodle.domain.Doodle;
import com.alom.dorundorunbe.domain.doodle.repository.DoodleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoodleService {

    private final DoodleRepository doodleRepository;
    private final UserRepository userRepository;
    private final UserDoodleRepository userDoodleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDoodleService userDoodleService;

    @Transactional
    public DoodleResponseDto createDoodle(DoodleRequestDto doodleRequestDto) { //doodle 생성 기능
        //Doodle 생성
        Doodle doodle = Doodle.builder()
                .name(doodleRequestDto.getName())
                .weeklyGoalDistance(doodleRequestDto.getWeeklyGoalDistance())
                .weeklyGoalCount(doodleRequestDto.getWeeklyGoalCount())
                .goalCadence(doodleRequestDto.getGoalCadence())
                .goalPace(doodleRequestDto.getGoalPace())
                .goalParticipationCount(doodleRequestDto.getGoalParticipationCount())
                .password(doodleRequestDto.getPassword())
                .maxParticipant(doodleRequestDto.getMaxParticipant())
                .participants(new ArrayList<>())
                .build();

        Doodle savedDoodle = doodleRepository.save(doodle);
        UserDoodle userDoodle = userDoodleService.createUserDoodle(savedDoodle.getId(), doodleRequestDto.getUserId());

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
        doodle.setWeeklyGoalDistance(doodleRequestDto.getWeeklyGoalDistance());
        doodle.setWeeklyGoalCount(doodleRequestDto.getWeeklyGoalCount());
        doodle.setGoalCadence(doodleRequestDto.getGoalCadence());
        doodle.setMaxParticipant(doodleRequestDto.getMaxParticipant());
        doodle.setGoalPace(doodleRequestDto.getGoalPace());
        doodle.setPassword(doodleRequestDto.getPassword());
        doodle.setGoalParticipationCount(doodleRequestDto.getGoalParticipationCount());

        Doodle updatedDoodle = doodleRepository.save(doodle);
        return DoodleResponseDto.from(updatedDoodle);
    }

    public DoodleResponseDto addParticipantToDoodle(Long doodleId, Long userId, String password) { //참가자 추가
        Doodle doodle = doodleRepository.findById(doodleId).orElseThrow(()->new RuntimeException("Doodle not found"));
        //비밀번호 검증
        if (!passwordEncoder.matches(password, doodle.getPassword())){
            throw new IllegalArgumentException("Wrong password");
        }

        //참가자 중복 체크
        if (doodle.IsDuplicatedParticipant(doodle, userId)){
            throw new IllegalArgumentException("Duplicated Participant");
        }

        //참가자 인원 제한 수 체크
        if (!doodle.checkCanAddParticipant(doodle.getParticipants().size())){
            throw new IllegalArgumentException("Full participants");
        }

        userDoodleService.addParticipantsToUserDoodle(doodleId, userId);
        return DoodleResponseDto.from(doodle);
    }


    public DoodleResponseDto deleteParticipant(Long doodleId, Long userId){
        Doodle doodle = doodleRepository.findById(doodleId).
                orElseThrow(()->new RuntimeException("NOT FOUND"));
        User user = userRepository.findById(userId).
                orElseThrow(()->new RuntimeException("NOT FOUND"));
        UserDoodle userDoodle = userDoodleRepository.findByDoodleAndUser(doodle, user).
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
        UserDoodle userDoodle = userDoodleRepository.findByDoodleAndUser(doodle, user)
                .orElseThrow(()->new IllegalArgumentException("NOT FOUND"));
        userDoodle.setStatus(status);
        userDoodleRepository.save(userDoodle);
        return UserDoodleDto.from(userDoodle);
    }

    //doodle 비밀번호 변경
    public DoodleResponseDto updateDoodlePassword(Long doodleId, Long userId, String newPassword){
        Doodle doodle = doodleRepository.findById(doodleId).orElseThrow(()->new RuntimeException(("Doodle not found")));
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        UserDoodle userDoodle = userDoodleRepository.findByDoodleAndUser(doodle, user).orElseThrow(()->new RuntimeException("UserDoodle not found"));
        if (userDoodle.getRole()!=UserDoodleRole.CREATOR){
            throw new IllegalArgumentException("비밀번호를 변경할 자격이 없습니다.");
        }
        String encodedPassword = passwordEncoder.encode(newPassword);
        doodle.setPassword(encodedPassword);
        Doodle updatedDoodle = doodleRepository.save(doodle);
        return DoodleResponseDto.from(updatedDoodle);
    }

    //주마다 목표 달성 여부 확인
    public boolean isGoalAchieved(Long doodleId, Long userId, List<RunningRecord> runningRecords){
        Doodle doodle = doodleRepository.findById(doodleId).orElseThrow(()->new RuntimeException("Doodle not found"));
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        //주간 목표 거리 달성 여부
        double weeklyTotalDistance = runningRecords.stream()
                .mapToDouble(RunningRecord::getDistance)
                .sum();
        if (weeklyTotalDistance < doodle.getWeeklyGoalDistance()){
            return false;
        }
        //주간 목표 러닝 횟수 달성 여부
        if (runningRecords.size() < doodle.getWeeklyGoalCount()){
            return false;
        }
        //목표 페이스 달성 여부
        double averagePace = runningRecords.stream()
                .mapToDouble(RunningRecord::getPace)
                .average()
                .orElse(0.0);
        if (averagePace < doodle.getGoalPace()){
            return false;
        }
        //목표 케이던스 달성 여부
        double averageCadence = runningRecords.stream()
                .mapToDouble(RunningRecord::getCadence)
                .average()
                .orElse(0.0);
        if (averageCadence < doodle.getGoalCadence()){
            return false;
        }
        return true;
    }

}
