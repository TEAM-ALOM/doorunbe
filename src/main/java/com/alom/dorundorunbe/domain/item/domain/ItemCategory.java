package com.alom.dorundorunbe.domain.item.domain;

import lombok.Getter;

@Getter
public enum ItemCategory {
    CLOTHES("옷"),
    HAIR("머리"),
    ACCESSORY("악세사리"),
    BACKGROUND("배경");

    private final String description;

    ItemCategory(String description) {
        this.description = description;
    }
}
