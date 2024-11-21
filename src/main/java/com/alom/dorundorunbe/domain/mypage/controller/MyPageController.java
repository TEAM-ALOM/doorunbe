package com.alom.dorundorunbe.domain.mypage.controller;


import com.alom.dorundorunbe.domain.RunningRecord.domain.RunningRecord;
import com.alom.dorundorunbe.domain.mypage.dto.*;
import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.domain.user.domain.UserRepository;
import com.alom.dorundorunbe.domain.mypage.*;
import com.alom.dorundorunbe.domain.mypage.service.MyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Controller
public class MyPageController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MyPageService myPageService;

    @GetMapping("/myPage")
    public ResponseEntity<?> myPage(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optionalUser = userRepository.findByUserName(username);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            List<AchievementResponse> achievementResponses = myPageService.getAchievements(username);
            String rank = user.getRanking().getRank().toString();
            List<RunningRecord> runningRecords = myPageService.getRunningRecords(username);

            MyPageResponse myPageResponse = new MyPageResponse(user.getName(), user.getEmail(), achievementResponses, rank, runningRecords);
            return new ResponseEntity<>(myPageResponse, HttpStatus.OK);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }
    }

    @PutMapping("/myPage/updateUser")
    public ResponseEntity<String> updateUser(@RequestBody UserUpdateDTO userDTO){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optionalUser = userRepository.findByUserName(username);
        if(optionalUser.isPresent())
            return myPageService.updateByName(userDTO, username);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @PutMapping("/myPage/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody UserPasswordChangeDTO userPasswordChangeDTO){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optionalUser = userRepository.findByUserName(username);
        if(optionalUser.isPresent())
            return myPageService.updatePassword(userPasswordChangeDTO, username);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @DeleteMapping("/myPage/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestBody UserDeleteDTO userDeleteDTO){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optionalUser = userRepository.findByUserName(username);
        if(optionalUser.isPresent())
            return myPageService.deleteUser(userDeleteDTO, username);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    
}
