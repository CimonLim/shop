package org.shop.api.domain.user.controller.model;

import lombok.Builder;
import lombok.Getter;
import org.shop.api.domain.token.controller.model.TokenResponse;
import org.shop.db.user.enums.UserRole;
import org.shop.db.user.enums.UserStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class UserResponse {

    private String email;

    private String name;

    private UserRole role;

    private UserStatus status;

    private LocalDateTime createdAt;

}
