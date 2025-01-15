package com.alom.dorundorunbe.domain.doodle.dto;

import com.alom.dorundorunbe.domain.doodle.domain.Doodle;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class DoodleResponseDto {
    private Long id;
    private String name;
    private Double goalDistance;
    private Double goalCadence;
    private Double goalPace;
    private Integer goalParticipationCount;
    private Integer maxParticipant;
    private List<UserDoodleDto> participants;

    public static DoodleResponseDto from(Doodle doodle) {
        //doodle 엔티티를 userDoodleDto 리스트로 변환
        List<UserDoodleDto> userDoodleDtos = doodle.getParticipants().stream()
                .map(UserDoodleDto::from)
                .toList();

        //doodle entity 객체를 doodleResponsedto로 변환
       return DoodleResponseDto.builder()
               .id(doodle.getId())
               .name(doodle.getName())
               .goalDistance(doodle.getGoalDistance())
               .goalCadence(doodle.getGoalCadence())
               .goalPace(doodle.getGoalPace())
               .goalParticipationCount(doodle.getGoalParticipationCount())
               .maxParticipant(doodle.getMaxParticipant())
               .participants(userDoodleDtos)
               .build();
    }
}
