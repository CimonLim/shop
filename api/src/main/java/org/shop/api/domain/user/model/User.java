package org.shop.api.domain.user.model;

import lombok.*;
import org.shop.db.user.enums.UserRole;
import org.shop.db.user.enums.UserStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class User {

    private UUID id;

    private String email;

    private String password;

    private String name;

    private String phoneNumber;

    private String address;

    private UserRole role;

    private UserStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
