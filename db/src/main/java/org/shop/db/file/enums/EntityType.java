package org.shop.db.file.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EntityType {
    PRODUCT("상품"),
    USER("유저"),
    REVIEW("리뷰"),
    NOTICE("알림"),
    INQUIRY("문의"),
    ORDER("주문");

    private final String description;
}
