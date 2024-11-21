package com.alom.dorundorunbe.doodle.controller;

import com.alom.dorundorunbe.doodle.domain.Doodle;
import com.alom.dorundorunbe.doodle.dto.DoodleRequestDto;
import com.alom.dorundorunbe.doodle.service.DoodleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/doodle")
public class DoodleController {

    @Autowired
    private DoodleService doodleService;

    @PostMapping("/create")
    public ResponseEntity<Doodle> createDoodle(@RequestBody DoodleRequestDto doodleRequestDto) {
        Doodle newDoodle = doodleService.createDoodle(doodleRequestDto);
        return new ResponseEntity<>(newDoodle, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Doodle>> getAllDoodles(){
        List<Doodle> doodles = doodleService.getAllDoodles();
        if (doodles.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(doodles);
    }

    @GetMapping("/{doodleId}")
    public ResponseEntity<Doodle> getDoodle(@PathVariable Long doodleId) {
        Optional<Doodle> doodle = doodleService.getDoodleById(doodleId);
        return doodle.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{doodleId}")
    public ResponseEntity<Doodle> updateDoodle(@PathVariable Long doodleId, @RequestBody DoodleRequestDto doodleRequestDto){
        Optional<Doodle> updatedDoodle = doodleService.updateDoodle(doodleId, doodleRequestDto);
        return updatedDoodle.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{doodleId}")
    public ResponseEntity<Void> deleteDoodle(@PathVariable Long doodleId){
        boolean isDeleted = doodleService.deleteDoodle(doodleId);

        if (isDeleted){
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{doodleId}/User/{userId}")
    public ResponseEntity<String> addParticipantToDoodle(@PathVariable Long doodleId, @PathVariable Long userId){
        try{
            doodleService.addParticipants(doodleId, userId);
            return ResponseEntity.ok("참가자가 성공적으로 추가되었습니다.");
        }
        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{doodleId}/User/{userId}")
    public ResponseEntity<String> deleteParticipant(@PathVariable Long doodleId, @PathVariable Long userId){
        try{
            doodleService.deleteParticipant(doodleId, userId);
            return ResponseEntity.ok("참가자가 성공적으로 제거되었습니다.");
        }
        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
