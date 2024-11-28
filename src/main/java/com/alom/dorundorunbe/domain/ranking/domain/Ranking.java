package com.alom.dorundorunbe.domain.ranking.domain;

import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.global.enums.Tier;
import com.alom.dorundorunbe.global.util.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity @Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ranking")
public class Ranking extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_id")
    private Long id;



}
