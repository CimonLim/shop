package org.shop.db.adminUser;


import lombok.*;
import lombok.experimental.SuperBuilder;
import org.shop.db.BaseEntityUuid;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@ToString
@Table(name = "admin_users", schema = "app")
public class AdminUserEntity extends BaseEntityUuid {

    @Column(unique = true, nullable = false)
    private String email;

    @Setter
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 50)
    private String department;

    @Column(name = "employee_id", length = 50)
    private String employeeId;

    // 보안 관련 필드
    @Column(name = "mfa_enabled")
    @Builder.Default
    private Boolean mfaEnabled = false;

    @Column(name = "mfa_secret", length = 100)
    private String mfaSecret;

    @Column(name = "last_password_change")
    private LocalDateTime lastPasswordChange;

    @Column(name = "password_expire_date")
    private LocalDateTime passwordExpireDate;

    @Column(name = "account_locked")
    @Builder.Default
    private Boolean accountLocked = false;

    @Column(name = "login_attempts")
    @Builder.Default
    private Integer loginAttempts = 0;

    @Column(name = "access_start_time")
    private LocalTime accessStartTime;

    @Column(name = "access_end_time")
    private LocalTime accessEndTime;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;
}