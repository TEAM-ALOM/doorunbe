package com.alom.dorundorunbe.domain.doodle.service;

import com.alom.dorundorunbe.domain.doodle.domain.Doodle;
import com.alom.dorundorunbe.domain.doodle.domain.UserDoodle;
import com.alom.dorundorunbe.domain.doodle.domain.UserDoodleStatus;
import com.alom.dorundorunbe.domain.doodle.dto.UserDoodleRole;
import com.alom.dorundorunbe.domain.doodle.repository.DoodleRepository;
import com.alom.dorundorunbe.domain.doodle.repository.UserDoodleRepository;
import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDoodleServiceTest {

    @Mock
    private UserDoodleRepository userDoodleRepository;

    @Mock
    private DoodleRepository doodleRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDoodleService userDoodleService;

    @Test
    @DisplayName("createUserDoodle : Doodle 생성 시 참가자 초기화에 성공한다")
    public void createUserDoodle(){
        Long userId = 1L;
        Long doodleId = 1L;

        User user = User.builder()
                .id(1L)
                .name("testUser")
                .build();

        Doodle doodle = Doodle.builder()
                .id(1L)
                .name("testDoodle")
                .participants(new ArrayList<>())
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
        when(doodleRepository.findById(doodleId)).thenReturn(Optional.ofNullable(doodle));

        UserDoodle result = userDoodleService.createUserDoodle(doodleId, userId);

        assertNotNull(result);
        assertThat(result.getRole()).isEqualTo(UserDoodleRole.CREATOR);
        assertThat(doodle.getParticipants().size()).isEqualTo(1);

        verify(userDoodleRepository, times(1)).save(any(UserDoodle.class));
        verify(doodleRepository, times(1)).save(any(Doodle.class));
    }

    @Test
    @DisplayName("addParticipantsToUserDoodle : Doodle방에 참가자를 업데이트 하는데 성공한다")
    public void addParticipantsToUserDoodle(){
        Long userId = 1L;
        Long doodleId = 1L;

        User user = User.builder()
                .id(1L)
                .name("testUser")
                .build();

        Doodle doodle = Doodle.builder()
                .id(1L)
                .name("testDoodle")
                .participants(new ArrayList<>())
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
        when(doodleRepository.findById(doodleId)).thenReturn(Optional.ofNullable(doodle));

        //when
        UserDoodle result = userDoodleService.addParticipantsToUserDoodle(doodleId, userId);

        //then
        assertNotNull(result);
        assertThat(doodle.getParticipants().size()).isEqualTo(1);
        assertThat(result.getRole()).isEqualTo(UserDoodleRole.PARTICIPANT);

        verify(userDoodleRepository, times(1)).save(result);
        verify(doodleRepository, times(1)).save(doodle);
    }
}
