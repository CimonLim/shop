package org.shop.admin.domain.adminuser.controller.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class AdminUserResponse {

    private UUID id;

    private String email;

    private String name;

    private String department;

    private String employeeId;

    private String passwordExpireDate;

    private Integer loginAttempts;

    private LocalDateTime createdAt;

}
