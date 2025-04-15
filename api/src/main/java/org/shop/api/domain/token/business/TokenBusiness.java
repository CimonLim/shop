package org.shop.api.domain.token.business;

import lombok.RequiredArgsConstructor;
import org.shop.api.common.annotation.Business;
import org.shop.api.common.error.ServerErrorCode;
import org.shop.api.common.exception.ApiException;
import org.shop.api.domain.token.controller.model.TokenResponse;
import org.shop.api.domain.token.converter.TokenConverter;
import org.shop.api.domain.token.model.RefreshTokenDto;
import org.shop.api.domain.token.service.TokenService;
import org.shop.db.BaseEntityUuid;
import org.shop.db.user.UserEntity;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Business
public class TokenBusiness {

    private final TokenService tokenService;

    public TokenResponse issueToken(UserEntity userEntity){

        return Optional.ofNullable(userEntity)
            .map(BaseEntityUuid::getId)
            .map(this::issueToken)
            .orElseThrow(
                ()-> new ApiException(ServerErrorCode.NULL_POINT)
            );
    }

    private TokenResponse issueToken(UUID userId){

        return Optional.ofNullable(userId)
                .map(it -> {
                    var accessToken = tokenService.issueAccessToken(it);
                    var refreshToken = tokenService.issueRefreshToken(it);
                    return TokenConverter.toResponse(accessToken, refreshToken);
                })
                .orElseThrow(
                        ()-> new ApiException(ServerErrorCode.NULL_POINT)
                );
    }

    public TokenResponse refreshToken(String refreshToken){
        RefreshTokenDto storedRefreshToken =  tokenService.validationRefreshToken(refreshToken);
        UUID userId = tokenService.deleteRefreshTokenFromRedis(storedRefreshToken);
        return issueToken(userId);
    }

    public UUID validationAccessToken(String accessToken){
        return tokenService.validationToken(accessToken);
    }

}
