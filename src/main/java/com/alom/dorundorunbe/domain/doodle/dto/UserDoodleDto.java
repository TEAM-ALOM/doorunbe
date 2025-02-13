package com.alom.dorundorunbe.domain.doodle.dto;

import com.alom.dorundorunbe.domain.doodle.domain.UserDoodle;
import com.alom.dorundorunbe.domain.doodle.domain.UserDoodleStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserDoodleDto {
    private Long id;
    private Long userId;
    private String userName;
    private Long doodleId;
    private UserDoodleStatus status;
    private LocalDate joinDate;
    private UserDoodleRole role;

    public static UserDoodleDto from(UserDoodle userDoodle) {
        if (userDoodle == null) {
            throw new IllegalArgumentException("UserDoodle cannot be null");
        }

        //userDoodle을 userDoodleDto로 변환
        return UserDoodleDto.builder()
                .userId(userDoodle.getUser().getId())
                .doodleId(userDoodle.getDoodle().getId())
                .status(userDoodle.getStatus())
                .joinDate(userDoodle.getJoinDate())
                .role(userDoodle.getRole())
                .build();
    }
}
