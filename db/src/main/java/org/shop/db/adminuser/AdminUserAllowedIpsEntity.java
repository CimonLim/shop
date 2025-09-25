package org.shop.db.adminuser;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.shop.db.BaseEntityUuid;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@ToString
@Table(name = "admin_user_allowed_ips", schema = "app")
public class AdminUserAllowedIpsEntity extends BaseEntityUuid {

    @Column(name = "admin_user_id")
    private UUID adminUserId;

    @Column(name = "ip_range", length = 50, nullable = false)
    private String ipRange;

    @Column(name = "description", length = 200)
    private String description;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_by")
    private UUID createdBy;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_by")
    private UUID updatedBy;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}