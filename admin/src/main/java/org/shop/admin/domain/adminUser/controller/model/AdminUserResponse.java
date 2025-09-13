package org.shop.admin.domain.adminUser.controller.model;

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

    private String employee_id;

    private String password_expire_date;

    private Integer login_attempts;

    private LocalDateTime createdAt;

}
