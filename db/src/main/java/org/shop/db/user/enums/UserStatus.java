package org.shop.db.user.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserStatus {

    REGISTERED("REGISTERED"),
    UNREGISTERED("UNREGISTERED"),
    ;

    private final String description;
}
