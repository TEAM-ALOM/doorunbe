package com.alom.dorundorunbe.domain.mypage.controller;

import com.alom.dorundorunbe.domain.RunningRecord.domain.RunningRecord;
import com.alom.dorundorunbe.domain.mypage.dto.AchievementResponse;
import com.alom.dorundorunbe.domain.mypage.dto.UserDeleteDTO;
import com.alom.dorundorunbe.domain.mypage.dto.UserUpdateDTO;
import com.alom.dorundorunbe.domain.mypage.service.MyPageService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(MyPageController.class)
@AutoConfigureMockMvc
class MyPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MyPageService myPageService;

    @Test
    @WithMockUser(username = "testUser")
    void myPageForReturnUserInfo() throws Exception {
        String username = "testUser";
        Mockito.when(myPageService.getAchievements(username))
                .thenReturn(List.of(new AchievementResponse(1L, "testAchievement")));
        Mockito.when(myPageService.getUserRank(username)).thenReturn("TestRank");
        Mockito.when(myPageService.getUserEmail(username)).thenReturn("testUser@test.com");
        Mockito.when(myPageService.getRunningRecords(username))
                .thenReturn(List.of(new RunningRecord())); //mock data 채워야 함.

        mockMvc.perform(get("/myPage"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(username))
                .andExpect(jsonPath("$.email").value("testUser@test.com"))
                .andExpect(jsonPath("$.rank").value("TestRank"));
    }

    @Test
    @WithMockUser(username = "testUser")
    void myPageReturnFailedUserNotFound() throws Exception {
        String username = "testUser";
        Mockito.when(myPageService.getAchievements(username)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/myPage"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found"));
    }

    @Test
    @WithMockUser(username = "testUser")
    void updateUserUpdateSuccess() throws Exception {
        String username = "testUser";

        Mockito.when(myPageService.updateByName(any(UserUpdateDTO.class), eq(username)))
                .thenReturn(ResponseEntity.ok("User Update Success"));

        mockMvc.perform(put("/myPage/updateUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content("""
                        {
                          "name": "newUser",
                          "nickname": "newTest",
                          "age": 50
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(content().string("User Update Success"));
    }

    @Test
    @WithMockUser(username = "testUser")
    void updateUserReturnBadRequestWhenInvalidData() throws Exception {

        Mockito.when(myPageService.updateByName(any(UserUpdateDTO.class), eq("testUser")))
                .thenReturn(ResponseEntity.badRequest().body("Age must be greater than zero"));

        mockMvc.perform(put("/myPage/updateUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content("""
                        {
                          "name": null,
                          "nickname": "newNickName",
                          "age": -1
                        }
                        """))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Age must be greater than zero"));
    }

    @Test
    @WithMockUser(username = "testUser")
    void deleteUserSuccess() throws Exception {
        String username = "testUser";

        Mockito.when(myPageService.deleteUser(any(UserDeleteDTO.class), eq(username)))
                .thenReturn(ResponseEntity.ok("User Deleted Success"));

        mockMvc.perform(delete("/myPage/deleteUser")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                        "password": "testPassword"
                        
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(content().string("User Deleted Success"));
    }

    @Test
    @WithMockUser(username = "testUser")
    void deleteUser_ReturnsBadRequest_WhenPasswordDoesNotMatch() throws Exception {
        Mockito.when(myPageService.deleteUser(any(UserDeleteDTO.class), eq("testUser")))
                .thenReturn(ResponseEntity.badRequest().body("Password does not match"));

        mockMvc.perform(delete("/myPage/deleteUser")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "password": "wrongPassword"
                        }
                        """))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Password does not match"));
    }
}