package org.shop.db.user.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserRole {

    USER("user"),
    ;

    private final String description;
}
