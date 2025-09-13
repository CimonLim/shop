package org.shop.db.adminUser;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.shop.db.BaseEntityUuid;

import jakarta.persistence.*;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@ToString
@Table(name = "admin_sessions", schema = "app")
public class AdminSessionEntity extends BaseEntityUuid {

    @Column(name = "admin_user_id", nullable = false)
    private UUID adminUserId;

    @Column(name = "access_token", nullable = false, unique = true)
    private String accessToken;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "ip_address", columnDefinition = "inet")
    private InetAddress ipAddress;

    @Column(name = "user_agent", columnDefinition = "TEXT")
    private String userAgent;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Setter
    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "last_accessed_at")
    @Setter
    private LocalDateTime lastAccessedAt;


}