package com.alom.dorundorunbe.doodle.domain;

import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.global.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

@Entity @Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Table(name = "user_doodle")
public class UserDoodle extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "doodle_id", nullable = false)
    private Doodle doodle;

    @Enumerated(EnumType.STRING)
    private UserDoodleStatus status; //참가 상태

    private LocalDate joinDate; //참가날짜

}
