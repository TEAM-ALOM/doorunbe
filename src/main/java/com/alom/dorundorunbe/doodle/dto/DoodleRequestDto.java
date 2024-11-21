package com.alom.dorundorunbe.doodle.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoodleRequestDto {
    private String name;
    private double goalDistance;
    private double goalCadence;
    private double goalPace;
    private int goalParticipationCount;
    private String password;
    private int maxParticipant;

}
