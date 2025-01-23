package com.alom.dorundorunbe.domain.mypage.service;

import com.alom.dorundorunbe.domain.achievement.repository.UserAchievementRepository;
import com.alom.dorundorunbe.domain.runningrecord.domain.RunningRecord;
import com.alom.dorundorunbe.domain.runningrecord.repository.RunningRecordRepository;
import com.alom.dorundorunbe.domain.achievement.domain.UserAchievement;
import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.domain.user.repository.UserRepository;
import com.alom.dorundorunbe.domain.mypage.dto.AchievementResponse;
import com.alom.dorundorunbe.domain.mypage.dto.UserUpdateDTO;
import com.alom.dorundorunbe.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final UserRepository userRepository;

    private final UserService userService;

    private final RunningRecordRepository runningRecordRepository;

    private final UserAchievementRepository userAchievementRepository;

    public List<RunningRecord> getRunningRecords(String username) {
        Optional<User> userOpt = userRepository.findByEmail(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            List<RunningRecord> runningRecords = runningRecordRepository.findAllByUser(user);
            runningRecords.sort(Comparator.comparing(RunningRecord::getDate).reversed());
            return runningRecords;
        }
        else return null;
    }
    public List<AchievementResponse> getAchievements(String username) {
        Optional<User> userOpt = userRepository.findByEmail(username);
        if (userOpt.isPresent()) {
            List<UserAchievement> userAchievements = userAchievementRepository.findAllByUserEmail(username);
            return userAchievements.stream()
                    .map(ua->new AchievementResponse(
                            ua.getAchievement().getId(),
                            ua.getAchievement().getName()

                    ))
                    .collect(Collectors.toList());
        }
        else return null;
    }

    public String getUserRank(String username) {
        Optional<User> userOpt = userRepository.findByEmail(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return user.getRanking().toString();
        }
        else return null;
    }
    public String getUserNickname(String username) {
        Optional<User> userOpt = userRepository.findByEmail(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return user.getNickname();
        }
        else return null;
    }

    public boolean checkNickNameDuplicate(String nickName) {
        return userRepository.existsByNickname(nickName);
    }

    public ResponseEntity<String> updateByUsername(UserUpdateDTO userDTO, String username) {
        Optional<User> userOpt = userRepository.findByEmail(username);
        if (userOpt.isPresent()) {
            User existingUser = userOpt.get();
            if(userDTO.getNickname() == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nickname is required");
            if(checkNickNameDuplicate(userDTO.getNickname()))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nickname already exists");
            existingUser.setNickname(userDTO.getNickname());
            userRepository.save(existingUser);
            return ResponseEntity.status(HttpStatus.OK).body("User updated successfully");
        }
        else return null;

    }

    public void updateNickname(Long userId, String nickname) {
        User user = userService.findById(userId);
        user.updateNickname(nickname);
    }

    public ResponseEntity<String> deleteUser(String username) {
        Optional<User> userOpt = userRepository.findByEmail(username);
        if (userOpt.isPresent()) {
            User existingUser = userOpt.get();
            // oauth2 주체와 연결 끊는 로직 필요
            userRepository.delete(existingUser);
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
        }
        else return null;
    }
}