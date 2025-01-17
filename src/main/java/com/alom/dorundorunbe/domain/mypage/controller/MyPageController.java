package com.alom.dorundorunbe.domain.mypage.controller;


import com.alom.dorundorunbe.domain.runningrecord.domain.RunningRecord;
import com.alom.dorundorunbe.domain.mypage.dto.*;
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

@Controller
public class MyPageController {

    @Autowired
    private MyPageService myPageService;

    @GetMapping("/myPage")
    public ResponseEntity<?> myPage(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        List<AchievementResponse> achievementResponses = myPageService.getAchievements(username);
        if(achievementResponses.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

        String rank = myPageService.getUserRank(username);
        if(rank == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

        List<RunningRecord> runningRecords = myPageService.getRunningRecords(username);
        if(runningRecords.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

        String userEmail = myPageService.getUserEmail(username);
        if(userEmail == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        MyPageResponse myPageResponse = new MyPageResponse(username, userEmail, achievementResponses, rank, runningRecords);
        return new ResponseEntity<>(myPageResponse, HttpStatus.OK);

    }

    @PutMapping("/myPage/updateUser")
    public ResponseEntity<String> updateUser(@RequestBody UserUpdateDTO userDTO){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        ResponseEntity<String> response = myPageService.updateByName(userDTO, username);
        if(response == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        else return response;
    }

    @PutMapping("/myPage/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody UserPasswordChangeDTO userPasswordChangeDTO){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(myPageService.updatePassword(userPasswordChangeDTO, username) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        else return myPageService.updatePassword(userPasswordChangeDTO, username);
    }

    @DeleteMapping("/myPage/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestBody UserDeleteDTO userDeleteDTO){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(myPageService.deleteUser(userDeleteDTO, username) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        else return myPageService.deleteUser(userDeleteDTO, username);
    }

    
}
