package org.shop.admin.domain.adminuser.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Builder
public class AdminUser {

    private UUID id;

    private String email;

    private String name;

    private String department;

    private String employeeId;

    private Boolean mfaEnabled;

    private LocalDateTime lastPasswordChange;

    private LocalDateTime passwordExpireDate;

    private Boolean accountLocked;

    private Integer loginAttempts;

    private LocalTime accessStartTime;

    private LocalTime accessEndTime;

    private Boolean isActive;

    private LocalDateTime lastLoginAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
