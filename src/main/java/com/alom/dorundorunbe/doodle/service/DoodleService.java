package com.alom.dorundorunbe.doodle.service;

import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.doodle.domain.UserDoodle;
import com.alom.dorundorunbe.User.Repository.UserRepository;
import com.alom.dorundorunbe.doodle.domain.Doodle;
import com.alom.dorundorunbe.doodle.domain.UserDoodleStatus;
import com.alom.dorundorunbe.doodle.dto.DoodleRequestDto;
import com.alom.dorundorunbe.doodle.repository.DoodleRepository;
import com.alom.dorundorunbe.doodle.repository.UserDoodleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DoodleService {
    @Autowired
    private DoodleRepository doodleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDoodleRepository userDoodleRepository;

    public Doodle createDoodle(DoodleRequestDto doodleRequestDto) { //doodle 생성 기능
        Doodle doodle = Doodle.builder()
                .name(doodleRequestDto.getName())
                .goalDistance(doodleRequestDto.getGoalDistance())
                .goalCadence(doodleRequestDto.getGoalCadence())
                .goalPace(doodleRequestDto.getGoalPace())
                .goalParticipationCount(doodleRequestDto.getGoalParticipationCount())
                .password(doodleRequestDto.getPassword())
                .maxParticipant(doodleRequestDto.getMaxParticipant())
                .build();
        return doodleRepository.save(doodle); //수정사항 dto로 변환해서 넘길것.., 이름 바꾸기
    }

    public List<Doodle> getAllDoodles(){ //doodle 전체 조회
        return doodleRepository.findAll();
    }

    public Optional<Doodle> getDoodleById(Long id) { //doodle 상세 조회
        return doodleRepository.findById(id);
    }

    public boolean deleteDoodle(Long id){
        if (doodleRepository.existsById(id)){
            doodleRepository.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }

    public Optional<Doodle> updateDoodle(Long id, DoodleRequestDto doodleRequestDto) {
        return doodleRepository.findById(id).map(doodle -> {
            doodle.setName(doodleRequestDto.getName());
            doodle.setGoalDistance(doodleRequestDto.getGoalDistance());
            doodle.setGoalCadence(doodleRequestDto.getGoalCadence());
            doodle.setGoalPace(doodleRequestDto.getGoalPace());
            doodle.setPassword(doodleRequestDto.getPassword());
            doodle.setMaxParticipant(doodleRequestDto.getMaxParticipant());
            return doodleRepository.save(doodle);
        });
    }

    public void addParticipants(Long doodleId, Long userId) { //참가자 추가
        Doodle doodle = doodleRepository.findById(doodleId).orElseThrow(()->new RuntimeException("존재하지 않는 Doodle 입니다."));
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("존재하지 않는 유저 ID 입니다."));
        //Doodle과 user 관계추가
        UserDoodle userDoodle = new UserDoodle();
        userDoodle.setDoodle(doodle);
        userDoodle.setUser(user);
        userDoodle.setStatus(UserDoodleStatus.PARTICIPATING);
        userDoodle.setJoinDate(LocalDate.now());
        //userDoodle 저장
        userDoodleRepository.save(userDoodle);
        //참가자 리스트에 추가
        doodle.getParticipants().add(userDoodle);
        doodleRepository.save(doodle);
    }

    public void deleteParticipant(Long doodleId, Long userId){
        Doodle doodle = doodleRepository.findById(doodleId).
                orElseThrow(()->new RuntimeException("존재하지 않는 Doodle 입니다."));
        User user = userRepository.findById(userId).
                orElseThrow(()->new RuntimeException("존재하지 않는 유저 ID 입니다."));
        UserDoodle userDoodle = userDoodleRepository.findByDoodleAndUser(doodleId, userId).
                orElseThrow(()->new RuntimeException("유저가 해당 Doodle에 존재하지 않습니다."));

        userDoodleRepository.delete(userDoodle);
        doodle.getParticipants().remove(userDoodle);
        doodleRepository.save(doodle);
   }

   //참가자 doodle 완료 상태 업데이트 로직 구현(완료 기준..??)


}
