package org.shop.api.domain.token.converter;

import lombok.RequiredArgsConstructor;
import org.shop.api.common.annotation.Converter;
import org.shop.api.common.error.ServerErrorCode;
import org.shop.api.common.exception.ApiException;
import org.shop.api.domain.token.controller.model.AccessTokenResponse;
import org.shop.api.domain.token.controller.model.TokenResponse;
import org.shop.api.domain.token.model.TokenDto;

import java.util.Objects;

@Converter
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
