package com.alom.dorundorunbe.domain.runningrecord.domain;

import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.global.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDate date;

    private Double distance;

    private Integer cadence;

    private Integer elapsedTime;

    private Double averageSpeed;

    private Double pace;

    @OneToMany(mappedBy = "runningRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RunningRecordItem> items = new ArrayList<>();

    @OneToMany(mappedBy = "runningRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GpsCoordinate> gpsCoordinates = new ArrayList<>();

    public void addRunningRecordItem(RunningRecordItem runningRecordItem){
        items.add(runningRecordItem);
        runningRecordItem.setRunningRecord(this);
    }

    public void addGpsCoordinate(GpsCoordinate gpsCoordinate) {
        gpsCoordinates.add(gpsCoordinate);
        gpsCoordinate.setRunningRecord(this);
    }

    public void calculatePace() {
        if (distance != null && elapsedTime != null && distance > 0 && elapsedTime > 0) {
            double paceInMinutesPerKm = (elapsedTime / 60.0) / (distance / 1000.0);
            this.pace = paceInMinutesPerKm;
        }
    }
}
