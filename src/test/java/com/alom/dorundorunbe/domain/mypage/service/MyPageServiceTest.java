package com.alom.dorundorunbe.domain.mypage.service;

import com.alom.dorundorunbe.domain.runningrecord.domain.RunningRecord;
import com.alom.dorundorunbe.domain.runningrecord.repository.RunningRecordRepository;
import com.alom.dorundorunbe.domain.mypage.dto.UserUpdateDTO;
import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MyPageServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RunningRecordRepository runningRecordRepository;

    @InjectMocks
    private MyPageService myPageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("사용자 정보 수정 성공")
    void updateUserSuccess(){
        String username = "testUser";
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setNickname("newNickName");

        User existingUser = new User();
        existingUser.setNickname("oldNickName");

        Mockito.when(userRepository.findByEmail(username)).thenReturn(Optional.of(existingUser));
        ResponseEntity<String> response = myPageService.updateByEmail(userUpdateDTO, username);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User updated successfully", response.getBody());

        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(username);
        Mockito.verify(userRepository, Mockito.times(1)).save(existingUser);
    }

    @Test
    @DisplayName("사용자 닉네임 수정 실패: 중복")
    void updateUserNickNameDuplicate() {
        String username = "testUser";
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setNickname("newNickName");

        User existingUser = new User();
        existingUser.setNickname("existingNickName");

        Mockito.when(userRepository.findByEmail(username)).thenReturn(Optional.of(existingUser));
        Mockito.when(userRepository.existsByEmail(userUpdateDTO.getNickname())).thenReturn(true);

        ResponseEntity<String> response = myPageService.updateByEmail(userUpdateDTO, username);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Username already exists", response.getBody());
    }

    @Test
    @DisplayName("러닝 기록 조회 성공")
    void getRunningRecordsSuccess() {
        // Given: 테스트 데이터 준비
        String username = "testUser";
        User existingUser = new User();
        existingUser.setEmail(username);

        RunningRecord record1 = new RunningRecord();
        record1.setDate(LocalDate.from(LocalDateTime.of(2025, 1, 10, 0, 0)));

        RunningRecord record2 = new RunningRecord();
        record2.setDate(LocalDate.from(LocalDateTime.of(2025, 1, 11, 0, 0)));

        // 수정 가능한 리스트로 변경
        List<RunningRecord> runningRecords = new ArrayList<>(List.of(record1, record2));

        // Mock 설정
        Mockito.when(userRepository.findByEmail(Mockito.eq(username))).thenReturn(Optional.of(existingUser));
        Mockito.when(runningRecordRepository.findAllByUser(Mockito.eq(existingUser))).thenReturn(runningRecords);

        // When: 테스트 실행
        List<RunningRecord> result = myPageService.getRunningRecords(username);

        // Then: 결과 검증
        assertEquals(2, result.size());
        assertEquals(LocalDateTime.of(2025, 1, 10, 0, 0), result.get(1).getDate());
        assertEquals(LocalDateTime.of(2025, 1, 11, 0, 0), result.get(0).getDate());

        // Mock 호출 검증
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(username);
        Mockito.verify(runningRecordRepository, Mockito.times(1)).findAllByUser(existingUser);
    }








}