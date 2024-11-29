package com.alom.dorundorunbe.domain.doodle.service;

import com.alom.dorundorunbe.domain.doodle.domain.Doodle;
import com.alom.dorundorunbe.domain.doodle.domain.UserDoodle;
import com.alom.dorundorunbe.domain.doodle.domain.UserDoodleStatus;
import com.alom.dorundorunbe.domain.doodle.dto.DoodleRequestDto;
import com.alom.dorundorunbe.domain.doodle.dto.DoodleResponseDto;
import com.alom.dorundorunbe.domain.doodle.dto.UserDoodleDto;
import com.alom.dorundorunbe.domain.doodle.dto.UserDoodleRole;
import com.alom.dorundorunbe.domain.doodle.repository.DoodleRepository;
import com.alom.dorundorunbe.domain.doodle.repository.UserDoodleRepository;
import com.alom.dorundorunbe.domain.user.domain.Gender;
import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.domain.user.repository.UserRepository;
import com.alom.dorundorunbe.global.enums.Tier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DoodleServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDoodleRepository userDoodleRepository;

    @Mock
    private DoodleRepository doodleRepository;

    @InjectMocks
    private DoodleService doodleService;

    private static User user;
    private static DoodleRequestDto doodleRequestDto;
    private static Doodle doodle1;
    private static Doodle doodle2;
    private static UserDoodleDto userDoodleDto;
    private static UserDoodle userDoodle;

    @BeforeEach
    public void setUp(){
        user = User.builder()
                .id(1L)
                .nickname("runner123")
                .name("testUser")
                .email("example@example.com")
                .age(20)
                .cash(1000L)
                .tier(Tier.AMATEUR)
                .gender(Gender.FEMALE)
                .build();

        doodle1 = Doodle.builder()
                .id(1L)
                .name("Doodle 1")
                .goalDistance(100.0)
                .goalCadence(50.0)
                .goalPace(5.0)
                .goalParticipationCount(10)
                .password("password1")
                .maxParticipant(5)
                .participants(new ArrayList<>())  // 리스트 비워놓음
                .build();

        doodle2 = Doodle.builder()
                .id(2L)
                .name("Doodle 2")
                .goalDistance(200.0)
                .goalCadence(60.0)
                .goalPace(6.0)
                .goalParticipationCount(20)
                .password("password2")
                .maxParticipant(10)
                .participants(new ArrayList<>())  // 리스트 비워놓음
                .build();

        userDoodle = UserDoodle.builder()
                .id(1L)
                .user(user)
                .doodle(doodle1)
                .status(UserDoodleStatus.PARTICIPATING)
                .role(UserDoodleRole.PARTICIPANT)
                .joinDate(LocalDate.now())
                .build();

        doodleRequestDto = DoodleRequestDto.builder()
                .name("Test Doodle")
                .goalDistance(1.0)
                .goalCadence(2.0)
                .goalPace(3.0)
                .goalParticipationCount(10)
                .password("testPassword")
                .maxParticipant(10)
                .build();

    }

    @Test
    @DisplayName("createDoodle : Doodle 생성에 성공한다.")
    public void createDoodle() {
        //mock userRepository
        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.of(user));

        //mock doodleRepository
        Doodle savedDoodle = Doodle.builder()
                .id(1L)
                .name("Test Doodle")
                .goalDistance(1.0)
                .goalCadence(2.0)
                .goalPace(3.0)
                .goalParticipationCount(10)
                .password("testPassword")
                .maxParticipant(10)
                .participants(new ArrayList<>())
                .build();
        when(doodleRepository.save(any(Doodle.class))).thenReturn(savedDoodle);

        //mock userDoodleRepository
        UserDoodle userDoodle = new UserDoodle();
        userDoodle.setRole(UserDoodleRole.CREATOR);
        when(userDoodleRepository.save(any(UserDoodle.class))).thenReturn(userDoodle);

        //service call
        DoodleResponseDto responseDto = doodleService.createDoodle(doodleRequestDto, user.getId());

        //Assertions
        assertNotNull(responseDto);
        assertEquals("Test Doodle", responseDto.getName());

        //생성자 역할이 생성되었는지 확인
        verify(userDoodleRepository, times(1)).save(any(UserDoodle.class));
        assertEquals(UserDoodleRole.CREATOR, userDoodle.getRole());

        //doodleRepository가 제대로 저장되었는지 확인
        verify(doodleRepository, times(1)).save(any(Doodle.class));
    }

    @Test
    @DisplayName("getAllDoodles : Doodle 전체 조회에 성공한다.")
    public void getAllDoodles(){
        //given : DoodleRepository의 findAll()이 호출되었을 때 정의된 값 반환
        when(doodleRepository.findAll()).thenReturn(Arrays.asList(doodle1, doodle2));
        //when : getAllDoodles call
        List<DoodleResponseDto> doodleResponseDtos = doodleService.getAllDoodles();
        //then
        verify(doodleRepository, times(1)).findAll(); //findAll이 1번 호출되었는지
        assert doodleResponseDtos.size()==2;
        assert doodleResponseDtos.get(0).getName().equals("Doodle 1");
        assert doodleResponseDtos.get(1).getName().equals("Doodle 2");
    }

    @Test
    @DisplayName("getDoodleById : Doodle 조회에 성공한다.")
    public void getDoodleById(){
        //given
        when(doodleRepository.findById(anyLong())).thenReturn(Optional.of(doodle1));
        //when
        DoodleResponseDto doodleResponseDto = doodleService.getDoodleById(1L);
        //then
        assertNotNull(doodleResponseDto);
        assertEquals("Doodle 1", doodleResponseDto.getName());
        assertEquals(1L, doodleResponseDto.getId());

        verify(doodleRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("deleteDoodle : Doodle 삭제에 성공한다.")
    public void deleteDoodle(){
        Doodle doodle = Doodle.builder()
                .id(1L)
                .name("Delete Test Doodle")
                .goalDistance(1.0)
                .goalCadence(2.0)
                .goalPace(3.0)
                .goalParticipationCount(10)
                .password("testPassword")
                .maxParticipant(10)
                .participants(new ArrayList<>())
                .build();

        when(doodleRepository.findById(1L)).thenReturn(Optional.of(doodle));

        doNothing().when(doodleRepository).delete(doodle);

        doodleService.deleteDoodle(1L);

        verify(doodleRepository, times(1)).findById(1L);
        verify(doodleRepository, times(1)).delete(doodle);
    }

    @Test
    @DisplayName("updateDoodle : Doodle 수정에 성공한다.")
    public void updateDoodle(){
        Doodle oldDoodle = Doodle.builder()
                .id(1L)
                .name("Test Doodle")
                .goalDistance(1.0)
                .goalCadence(2.0)
                .goalPace(3.0)
                .goalParticipationCount(10)
                .password("testPassword")
                .maxParticipant(10)
                .participants(new ArrayList<>())
                .build();

        DoodleRequestDto updatedDoodleRequestDto = DoodleRequestDto.builder()
                .name("Updated Test Doodle")
                .goalDistance(2.0)
                .goalCadence(3.0)
                .goalPace(4.0)
                .goalParticipationCount(20)
                .maxParticipant(20)
                .build();

        when(doodleRepository.findById(1L)).thenReturn(Optional.of(oldDoodle));
        when(doodleRepository.save(any(Doodle.class))).thenReturn(oldDoodle);

        DoodleResponseDto responseDto = doodleService.updateDoodle(1L, updatedDoodleRequestDto);

        assertNotNull(responseDto);
        assertEquals("Updated Test Doodle", responseDto.getName());
        assertEquals(1L, responseDto.getId());

        verify(doodleRepository, times(1)).findById(1L);
        verify(doodleRepository, times(1)).save(oldDoodle);
    }

    @Test
    @DisplayName("addParticipantToDoodle : Doodle 참가자 추가에 성공한다.")
    public void addParticipantToDoodle(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(doodleRepository.findById(anyLong())).thenReturn(Optional.of(doodle1));

        when(userDoodleRepository.save(any(UserDoodle.class))).thenReturn(userDoodle);
        when(doodleRepository.save(any(Doodle.class))).thenReturn(doodle1);

        DoodleResponseDto doodleResponseDto = doodleService.addParticipantToDoodle(doodle1.getId(), user.getId());

        assertNotNull(doodleResponseDto);
        assertEquals("Doodle 1", doodleResponseDto.getName());
        assertEquals(1, doodleResponseDto.getParticipants().size());

        userDoodleDto = doodleResponseDto.getParticipants().get(0);
        assertEquals(user.getId(), userDoodleDto.getUserId());
        assertEquals(user.getName(), userDoodleDto.getUserName());
        assertEquals(UserDoodleStatus.PARTICIPATING, userDoodleDto.getStatus());
    }

    @Test
    @DisplayName("deleteParticipant : Doodle 참가자 삭제에 성공한다.")
    public void deleteParticipant(){
        when(doodleRepository.findById(anyLong())).thenReturn(Optional.of(doodle1));
        when(userDoodleRepository.findByDoodleIdAndUserId(doodle1, user))
                .thenReturn(Optional.of(userDoodle));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        doodle1.getParticipants().add(userDoodle);

        DoodleResponseDto doodleResponseDto = doodleService.deleteParticipant(doodle1.getId(), user.getId());

        assertNotNull(doodleResponseDto);
        assertEquals(doodle1.getId(), doodleResponseDto.getId());
        assertEquals(0, doodleResponseDto.getParticipants().size());

        verify(userDoodleRepository, times(1)).delete(userDoodle);
        verify(doodleRepository, times(1)).save(doodle1);
    }

    @Test
    @DisplayName("getParticipants : Doodle 참가자 전체 조회에 성공한다.")
    public void getParticipants(){
        List<UserDoodle> userDoodleList = new ArrayList<>();
        userDoodleList.add(userDoodle);
        doodle1.setParticipants(userDoodleList);

        when(doodleRepository.findById(1L)).thenReturn(Optional.of(doodle1));

        List<UserDoodleDto> participants = doodleService.getParticipants(1L);

        assertNotNull(participants);
        assertEquals(1, participants.size());
        assertEquals(user.getId(), participants.get(0).getUserId());

        verify(doodleRepository,times(1)).findById(1L);
    }

    @Test
    @DisplayName("updateParticipantStatus : Doodle 참가자 완료 상태 수정에 성공한다.")
    public void updateParticipantStatus(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(doodleRepository.findById(anyLong())).thenReturn(Optional.of(doodle1));
        when(userDoodleRepository.findByDoodleIdAndUserId(any(Doodle.class), any(User.class))).thenReturn(Optional.of(userDoodle));
        when(userDoodleRepository.save(any(UserDoodle.class))).thenReturn(userDoodle);

        userDoodleDto = doodleService.updateParticipantStatus(doodle1.getId(), user.getId(), UserDoodleStatus.COMPLETED);

        assertNotNull(userDoodleDto);
        assertEquals(UserDoodleStatus.COMPLETED, userDoodleDto.getStatus());

        verify(userDoodleRepository, times(1)).save(any(UserDoodle.class));
        verify(userDoodleRepository, times(1)).findByDoodleIdAndUserId(any(Doodle.class), any(User.class));
    }


}
