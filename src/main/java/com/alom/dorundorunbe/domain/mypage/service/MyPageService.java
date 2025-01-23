package com.alom.dorundorunbe.domain.mypage.service;

import com.alom.dorundorunbe.domain.achievement.repository.UserAchievementRepository;
import com.alom.dorundorunbe.domain.runningrecord.domain.RunningRecord;
import com.alom.dorundorunbe.domain.runningrecord.repository.RunningRecordRepository;
import com.alom.dorundorunbe.domain.achievement.domain.UserAchievement;
import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.domain.mypage.dto.AchievementResponse;
import com.alom.dorundorunbe.domain.mypage.dto.UserUpdateDTO;
import com.alom.dorundorunbe.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final UserService userService;

    private final RunningRecordRepository runningRecordRepository;

    private final UserAchievementRepository userAchievementRepository;

    public List<RunningRecord> getRunningRecords(String username) {
        User user = userService.findByEmail(username);
        List<RunningRecord> runningRecords = runningRecordRepository.findAllByUser(user);
        runningRecords.sort(Comparator.comparing(RunningRecord::getDate).reversed());
        return runningRecords;
    }

    public List<AchievementResponse> getAchievements(String username) {
        User user = userService.findByEmail(username);
        List<UserAchievement> userAchievements = userAchievementRepository.findAllByUser(user);
        return userAchievements.stream()
                .map(ua->new AchievementResponse(
                        ua.getAchievement().getId(),
                        ua.getAchievement().getName()
                ))
                .collect(Collectors.toList());
    }

    public String getUserRank(String username) {
        User user = userService.findByEmail(username);
        return user.getRanking().toString();
    }
    public String getUserNickname(String username) {
        User user = userService.findByEmail(username);
        return user.getNickname();
    }

    public boolean checkNickNameDuplicate(String nickName) {
        return userService.existsByNickname(nickName);
    }

    public ResponseEntity<String> updateByUsername(UserUpdateDTO userDTO, String username) {
        User user = userService.findByEmail(username);
        if(userDTO.getNickname() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nickname is required");
        if(checkNickNameDuplicate(userDTO.getNickname()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nickname already exists");
        user.setNickname(userDTO.getNickname());
        userService.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("User updated successfully");
    }

    public void updateNickname(Long userId, String nickname) {
        User user = userService.findById(userId);
        user.updateNickname(nickname);
    }

    public ResponseEntity<String> deleteUser(String username) {
        User user = userService.findByEmail(username);
        // oauth2 주체와 연결 끊는 로직 필요
        userService.delete(user);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
    }
}