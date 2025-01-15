package com.alom.dorundorunbe.domain.doodle.service;

import com.alom.dorundorunbe.domain.doodle.domain.Doodle;
import com.alom.dorundorunbe.domain.doodle.domain.UserDoodle;
import com.alom.dorundorunbe.domain.doodle.domain.UserDoodleStatus;
import com.alom.dorundorunbe.domain.doodle.dto.UserDoodleDto;
import com.alom.dorundorunbe.domain.doodle.dto.UserDoodleRole;
import com.alom.dorundorunbe.domain.doodle.repository.DoodleRepository;
import com.alom.dorundorunbe.domain.doodle.repository.UserDoodleRepository;
import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDoodleService {

    private final UserDoodleRepository userDoodleRepository;
    private final UserRepository userRepository;
    private final DoodleRepository doodleRepository;

    // 참가자 리스트가 null일 경우 초기화하는 메서드
    private void initializeParticipantsList(Doodle doodle) {
        if (doodle.getParticipants() == null) {
            doodle.setParticipants(new ArrayList<>());
        }
    }

    public UserDoodle createUserDoodle(Long doodleId, Long userId){
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("USER NOT FOUND"));
        Doodle doodle = doodleRepository.findById(doodleId).orElseThrow(()->new IllegalArgumentException("DOODLE NOT FOUND"));

        initializeParticipantsList(doodle);

        UserDoodle userDoodle = new UserDoodle();
        userDoodle.setStatus(UserDoodleStatus.PARTICIPATING);
        userDoodle.setRole(UserDoodleRole.CREATOR);
        userDoodle.setJoinDate(LocalDate.now());
        userDoodle.setUser(user);
        userDoodle.setDoodle(doodle);

        doodle.getParticipants().add(userDoodle);

        userDoodleRepository.save(userDoodle);
        doodleRepository.save(doodle);

        return userDoodle;
    }

    public UserDoodle addParticipantsToUserDoodle(Long doodleId, Long userId){
        //참가자가 방에 들어올때마다 호출됨
        User user = userRepository.findById(doodleId).orElseThrow(()->new IllegalArgumentException("USER NOT FOUND"));
        Doodle doodle = doodleRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("DOODLE NOT FOUND"));

        initializeParticipantsList(doodle);

        UserDoodle userDoodle = new UserDoodle();
        userDoodle.setStatus(UserDoodleStatus.PARTICIPATING);
        userDoodle.setRole(UserDoodleRole.PARTICIPANT); //참가자역할로 추가
        userDoodle.setJoinDate(LocalDate.now());
        userDoodle.setUser(user);
        userDoodle.setDoodle(doodle);

        doodle.getParticipants().add(userDoodle);

        userDoodleRepository.save(userDoodle);
        doodleRepository.save(doodle);

        return userDoodle;
    }
}
