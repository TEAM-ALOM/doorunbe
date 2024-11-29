package com.alom.dorundorunbe.domain.mypage.service;

import com.alom.dorundorunbe.domain.mypage.dto.UserUpdateDTO;
import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.domain.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MyPageServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MyPageService myPageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateUserSuccess(){
        String username = "testUser";
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setName("newUser");
        userUpdateDTO.setAge(25);
        userUpdateDTO.setNickname("newNickName");

        User existingUser = new User();
        existingUser.setName(username);
        existingUser.setAge(20);
        existingUser.setNickname("oldNickName");

        Mockito.when(userRepository.findByName(username)).thenReturn(Optional.of(existingUser));
        ResponseEntity<String> response = myPageService.updateByName(userUpdateDTO, username);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User updated successfully", response.getBody());

        Mockito.verify(userRepository, Mockito.times(1)).findByName(username);
        Mockito.verify(userRepository, Mockito.times(1)).save(existingUser);
    }
}