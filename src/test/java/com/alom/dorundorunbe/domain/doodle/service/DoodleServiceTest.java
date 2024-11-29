package com.alom.dorundorunbe.domain.doodle.service;

import com.alom.dorundorunbe.domain.doodle.dto.DoodleRequestDto;
import com.alom.dorundorunbe.domain.doodle.repository.DoodleRepository;
import com.alom.dorundorunbe.domain.doodle.repository.UserDoodleRepository;
import com.alom.dorundorunbe.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class DoodleServiceController {
    @Mock
    private DoodleRepository doodleRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserDoodleRepository userDoodleRepository;

    @InjectMocks
    private DoodleService doodleService;

    private DoodleRequestDto doodleRequestDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        doodleRequestDto = new DoodleRequestDto("새로운Doodle",
                1,2,3,4,"password",10);
    }

    @Test
    public void testCreateDoodle() {
        
    }
}
