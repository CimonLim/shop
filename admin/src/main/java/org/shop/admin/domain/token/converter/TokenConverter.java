package org.shop.admin.domain.token.converter;

import lombok.experimental.UtilityClass;
import org.shop.admin.domain.token.controller.model.AccessTokenResponse;
import org.shop.admin.domain.token.controller.model.TokenResponse;
import org.shop.admin.domain.token.model.TokenDto;
import org.shop.common.api.error.ServerErrorCode;
import org.shop.common.api.exception.ApiException;

import java.util.Objects;

@UtilityClass
public class TokenConverter {

    public static TokenResponse toResponse(
        TokenDto accessToken,
        TokenDto refreshToken
    ){
        Objects.requireNonNull(accessToken, ()->{throw new ApiException(ServerErrorCode.NULL_POINT);});
        Objects.requireNonNull(refreshToken, ()->{throw new ApiException(ServerErrorCode.NULL_POINT);});

        return TokenResponse.builder()
            .accessToken(accessToken.getToken())
            .accessTokenExpiredAt(accessToken.getExpiredAt())
            .refreshToken(refreshToken.getToken())
            .refreshTokenExpiredAt(refreshToken.getExpiredAt())
            .build();
    }

    public static AccessTokenResponse toResponse(
            TokenDto accessToken
    ){
        Objects.requireNonNull(accessToken, ()->{throw new ApiException(ServerErrorCode.NULL_POINT);});

        return AccessTokenResponse.builder()
                .accessToken(accessToken.getToken())
                .accessTokenExpiredAt(accessToken.getExpiredAt())
                .build();
    }
}
