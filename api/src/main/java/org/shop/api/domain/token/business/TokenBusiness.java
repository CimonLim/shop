package org.shop.api.domain.token.business;

import lombok.RequiredArgsConstructor;
import org.shop.api.common.annotation.Business;
import org.shop.api.common.error.ServerErrorCode;
import org.shop.api.common.exception.ApiException;
import org.shop.api.domain.token.controller.model.TokenResponse;
import org.shop.api.domain.token.converter.TokenConverter;
import org.shop.api.domain.token.service.TokenService;
import org.shop.db.BaseEntityUuid;
import org.shop.db.user.UserEntity;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Business
public class TokenBusiness {

    private final TokenService tokenService;
    private final TokenConverter tokenConverter;

    /**
     * 1. user entity user Id 추출
     * 2. access, refresh token 발행
     * 3. converter -> token response로 변경
     */
    public TokenResponse issueToken(UserEntity userEntity){

        return Optional.ofNullable(userEntity)
            .map(BaseEntityUuid::getId)
            .map(userId -> {
                var accessToken = tokenService.issueAccessToken(userId);
                var refreshToken = tokenService.issueRefreshToken(userId);
                return tokenConverter.toResponse(accessToken, refreshToken);
            })
            .orElseThrow(
                ()-> new ApiException(ServerErrorCode.NULL_POINT)
            );
    }

    public UUID validationAccessToken(String accessToken){
        var userId = tokenService.validationToken(accessToken);
        return userId;
    }

}
