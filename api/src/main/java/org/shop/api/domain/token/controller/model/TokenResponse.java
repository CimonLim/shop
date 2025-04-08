package org.shop.api.domain.token.controller.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
public class TokenResponse {

    private String accessToken;
    private LocalDateTime accessTokenExpiredAt;

    private String refreshToken;

    private LocalDateTime refreshTokenExpiredAt;
}
