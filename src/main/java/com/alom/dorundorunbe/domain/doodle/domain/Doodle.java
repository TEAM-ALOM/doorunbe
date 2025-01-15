package com.alom.dorundorunbe.domain.doodle.domain;

import com.alom.dorundorunbe.global.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity @Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Table(name = "doodle")
public class Doodle extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 32)
    private String name;

    @Column(nullable = false)
    private double weeklyGoalDistance;

    @Column(nullable = false)
    private int weeklyGoalCount;

    @Column(nullable = false)
    private double goalCadence;

    @Column(nullable = false)
    private double goalPace;

    @Column(nullable = false)
    private int goalParticipationCount;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private int maxParticipant;

    @OneToMany(mappedBy = "doodle", cascade = CascadeType.ALL)
    private List<UserDoodle> participants;

    //방 생성 시 참가자 수를 검증
    public boolean checkCanAddParticipant(int currentParticipants){
        return currentParticipants < maxParticipant;
    }

    //참가자 중복 검증
    public boolean IsDuplicatedParticipant(Doodle doodle, Long userId){
        return doodle.getParticipants().stream()
                .anyMatch(participant -> participant.getUser().getId().equals(userId));
    }

}
