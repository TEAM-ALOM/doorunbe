package com.alom.dorundorunbe.domain.doodle.domain;

import com.alom.dorundorunbe.domain.user.domain.User;
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
    private double goalDistance;

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
}
