package com.alom.dorundorunbe.domain.runningrecord.domain;

import com.alom.dorundorunbe.domain.item.dto.EquippedItemResponseDto;
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

    private LocalDate date;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Double distance;

    private Integer cadence;

    private Integer elapsedTime;

    private Double speed;

    private Boolean isFinished = false;

    @OneToMany(mappedBy = "runningRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RunningRecordItem> items = new ArrayList<>();

    public void addRunningRecordItem(RunningRecordItem runningRecordItem){
        items.add(runningRecordItem);
        runningRecordItem.setRunningRecord(this);
    }
}
