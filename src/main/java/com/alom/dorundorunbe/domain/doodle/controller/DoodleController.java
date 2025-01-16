package com.alom.dorundorunbe.domain.doodle.controller;

import com.alom.dorundorunbe.domain.doodle.domain.UserDoodleStatus;
import com.alom.dorundorunbe.domain.doodle.dto.DoodleRequestDto;
import com.alom.dorundorunbe.domain.doodle.dto.DoodleResponseDto;
import com.alom.dorundorunbe.domain.doodle.dto.UserDoodleDto;
import com.alom.dorundorunbe.domain.doodle.service.DoodleService;
import com.alom.dorundorunbe.domain.doodle.service.UserDoodleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/doodle")
public class DoodleController {

    @Autowired
    private DoodleService doodleService;
    @Autowired
    private UserDoodleService userDoodleService;

    @Autowired
    public DoodleController(DoodleService doodleService){
        this.doodleService = doodleService;
    }

    @PostMapping("/create/{userId}")
    @Operation(summary = "새로운 Doodle 생성")
    public ResponseEntity<DoodleResponseDto> createDoodle(@RequestBody DoodleRequestDto doodleRequestDto) {
        DoodleResponseDto doodleResponseDto = doodleService.createDoodle(doodleRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(doodleResponseDto);
    }

    @GetMapping
    @Operation(summary = "모든 Doodle 조회")
    public ResponseEntity<List<DoodleResponseDto>> getAllDoodles(){
        return ResponseEntity.ok(doodleService.getAllDoodles());
    }

    @GetMapping("/{doodleId}")
    @Operation(summary = "특정 Doodle 조회")
    public ResponseEntity<DoodleResponseDto> getDoodleById(@PathVariable("doodleId") Long doodleId) {
        return ResponseEntity.ok(doodleService.getDoodleById(doodleId));
    }

    @PutMapping("/{doodleId}")
    @Operation(summary = "특정 Doodle 수정")
    public ResponseEntity<DoodleResponseDto> updateDoodle(@PathVariable("doodleId") Long doodleId, @RequestBody DoodleRequestDto doodleRequestDto){
        DoodleResponseDto updatedDoodle = doodleService.updateDoodle(doodleId, doodleRequestDto);
        return ResponseEntity.ok(updatedDoodle);
    }

    @DeleteMapping("/{doodleId}")
    @Operation(summary = "특정 Doodle 삭제")
    public ResponseEntity<Void> deleteDoodle(@PathVariable("doodleId") Long doodleId){
        doodleService.deleteDoodle(doodleId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{doodleId}/User/{userId}")
    @Operation(summary = "특정 Doodle에 User 추가")
    public ResponseEntity<DoodleResponseDto> addParticipantToDoodle(@PathVariable("doodleId") Long doodleId, @PathVariable("userId") Long userId, @RequestParam("password") String password){
        return ResponseEntity.ok(doodleService.addParticipantToDoodle(doodleId, userId, password));
    }

    @DeleteMapping("/{doodleId}/User/{userId}")
    @Operation(summary = "특정 Doodle의 User 삭제")
    public ResponseEntity<DoodleResponseDto> deleteParticipant(@PathVariable("doodleId") Long doodleId, @PathVariable("userId") Long userId){
       return ResponseEntity.ok(doodleService.deleteParticipant(doodleId, userId));
    }

    @GetMapping("/{doodleId}/participants")
    @Operation(summary = "특정 Doodle의 모든 User 조회")
    public ResponseEntity<List<UserDoodleDto>> getParticipants(@PathVariable("doodleId") Long doodleId) {
        List<UserDoodleDto> userDoodleDtos = doodleService.getParticipants(doodleId);
        return ResponseEntity.ok(userDoodleDtos);
    }

    @PutMapping("/{doodleId}/participants/{userId}")
    @Operation(summary = "특정 Doodle의 User 완료 상태 변경")
    public ResponseEntity<UserDoodleDto> updateParticipantStatus(@PathVariable("doodleId") Long doodleId,
                                                                 @PathVariable("userId") Long userId,
                                                                 @RequestParam("status") UserDoodleStatus status){
        return ResponseEntity.ok(doodleService.updateParticipantStatus(doodleId, userId, status));
    }

    @PutMapping("/{doodleId}/password")
    @Operation(summary = "특정 Doodle 비밀번호를 변경")
    public ResponseEntity<DoodleResponseDto> updatedDoodlePassword(
            @PathVariable("doodleId") Long doodleId,
            @RequestParam("userId") Long userId,
            @RequestParam("newPassword") String newPassword){
        return ResponseEntity.ok(doodleService.updateDoodlePassword(doodleId, userId, newPassword));
    }

    //isGoalAchieved 구현
    @GetMapping("/{doodleId}/participants/{userId}")
    @Operation(summary = "특정 Doodle의 User 완료 상태 확인")
    public ResponseEntity<UserDoodleDto> isGoalAchieved(@PathVariable("doodleId") Long doodleId, @PathVariable("userId") Long userId){
        return ResponseEntity.ok(userDoodleService.isGoalAchieved(doodleId, userId));
    }
}
