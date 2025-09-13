package org.shop.admin.domain.token.controller.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AccessTokenResponse {

    private String accessToken;
    private LocalDateTime accessTokenExpiredAt;

}
