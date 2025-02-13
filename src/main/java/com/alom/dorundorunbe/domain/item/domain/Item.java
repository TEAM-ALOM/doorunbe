package com.alom.dorundorunbe.domain.item.domain;

import com.alom.dorundorunbe.domain.image.domain.Image;
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

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @Column(nullable = false)
    private Long cost;

    public void update(String name, ItemCategory itemCategory, Image image, Long cost) {
        this.name = name;
        this.itemCategory = itemCategory;
        this.image = image;
        this.cost = cost;
    }
}
