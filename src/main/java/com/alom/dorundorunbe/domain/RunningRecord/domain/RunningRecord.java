package com.alom.dorundorunbe.domain.RunningRecord.domain;

import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.global.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "running_record")
public class RunningRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime date;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private double distance;

    private int cadence;

    private long elapsedTime;

    private double speed;

    private boolean isFinished;
}
