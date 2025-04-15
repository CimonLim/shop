package org.shop.db.file.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EntityType {
    PRODUCT("PRODUCT"),
    USER("USER"),
    REVIEW("REVIEW"),
    NOTICE("NOTICE"),
    INQUIRY("INQUIRY"),
    ORDER("ORDER");

    private final String description;
}
