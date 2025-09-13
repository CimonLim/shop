package org.shop.admin.domain.token.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RefreshTokenDto {
    private String id;
    private UUID userId;
    private String refreshToken;
    private LocalDateTime expiration;
}