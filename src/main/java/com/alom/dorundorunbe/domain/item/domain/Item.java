package com.alom.dorundorunbe.domain.item.domain;

import com.alom.dorundorunbe.global.util.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 32)
    private String name;

    @Enumerated(EnumType.STRING)
    private ItemCategory itemCategory;

    // 어떤 형태로 저장할지 추후 추가

    @Column(nullable = false)
    private Long cost;

    public void update(String name, ItemCategory itemCategory, Long cost) {
        this.name = name;
        this.itemCategory = itemCategory;
        this.cost = cost;
    }
}
