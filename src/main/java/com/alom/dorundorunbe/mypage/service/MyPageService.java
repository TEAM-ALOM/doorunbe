package com.alom.dorundorunbe.mypage.service;


import com.alom.dorundorunbe.domain.RunningRecord.domain.RunningRecord;
import com.alom.dorundorunbe.domain.RunningRecord.domain.RunningRecordRepository;
import com.alom.dorundorunbe.domain.achievement.AchievementRepository;
import com.alom.dorundorunbe.domain.achievement.UserAchievement;
import com.alom.dorundorunbe.domain.user.User;
import com.alom.dorundorunbe.domain.user.UserRepository;
import com.alom.dorundorunbe.mypage.dto.AchievementResponse;
import com.alom.dorundorunbe.mypage.dto.UserDeleteDTO;
import com.alom.dorundorunbe.mypage.dto.UserPasswordChangeDTO;
import com.alom.dorundorunbe.mypage.dto.UserUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MyPageService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RunningRecordRepository runningRecordRepository;

    @Autowired
    private AchievementRepository achievementRepository;

    public List<RunningRecord> getRunningRecords(String username) {
        Optional<User> userOpt = userRepository.findByUserName(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            List<RunningRecord> runningRecords = runningRecordRepository.findByUser(user);
            Collections.sort(runningRecords, Comparator.comparing(RunningRecord::getDate).reversed());
            return runningRecords;
        }
        else return null;
    }
    public List<AchievementResponse> getAchievements(String username) {
        List<UserAchievement> userAchievements = achievementRepository.findByUserName(username);
        List<AchievementResponse> achievementResponses = userAchievements.stream()
                .map(ua->new AchievementResponse(
                        ua.getAchievement().getId(),
                        ua.getAchievement().getName(),
                        ua.getAchievement().getDescription()
                ))
                .collect(Collectors.toList());
        return achievementResponses;
    }
    public boolean checkNameDuplicate(String username) {return userRepository.existsByName(username);}
    public boolean checkNickNameDuplicate(String username, String nickName) {return userRepository.existsByNickName(username);}
    public boolean checkPasswordDuplicate(String password){return userRepository.existsByPassword(password);}
    public ResponseEntity<String> updateByName(UserUpdateDTO userDTO, String username) {
        User existingUser = userRepository.findByUserName(username).get();
        if(userDTO.getName() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name is required");
        if(checkNameDuplicate(userDTO.getName()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        if(userDTO.getAge() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Age is required");
        if(userDTO.getAge() <= 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Age must be greater than zero");
        if(userDTO.getNickname() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nickname is required");
        if(checkNickNameDuplicate(userDTO.getName(), userDTO.getNickname()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nickname already exists");
        existingUser.setNickname(userDTO.getNickname());
        existingUser.setAge(userDTO.getAge());
        existingUser.setName(userDTO.getName());
        userRepository.save(existingUser);
        return ResponseEntity.status(HttpStatus.OK).body("User updated successfully");
    }
    public ResponseEntity<String> updatePassword(UserPasswordChangeDTO userPasswordChangeDTO, String username){
        User existingUser = userRepository.findByUserName(username).get();
        if(userPasswordChangeDTO.getOldPassword() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old password is required");
        if(userPasswordChangeDTO.getNewPassword() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("New password is required");
        if(userPasswordChangeDTO.getNewPassword().length() < 8)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("New password must be at least 8 length");
        if(userPasswordChangeDTO.getNewPasswordConfirmation() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("New password confirmation is required");
        if(!userPasswordChangeDTO.getNewPassword().equals(userPasswordChangeDTO.getNewPasswordConfirmation()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("New password does not match");
        if(!userPasswordChangeDTO.getOldPassword().equals(existingUser.getPassword()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old password does not match");
        if(checkPasswordDuplicate(userPasswordChangeDTO.getNewPassword()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("New password already exists");
        existingUser.setPassword(userPasswordChangeDTO.getNewPassword());
        userRepository.save(existingUser);
        return ResponseEntity.status(HttpStatus.OK).body("User Password updated successfully");
    }

    public ResponseEntity<String> deleteUser(UserDeleteDTO userDeleteDTO, String username) {
        User existingUser = userRepository.findByUserName(username).get();
        if(!userDeleteDTO.getPassword().equals(existingUser.getPassword()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password does not match");
        userRepository.delete(existingUser);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
    }
}
