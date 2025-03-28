package org.shop.api.domain.user.business;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.Server;
import org.shop.api.common.annotation.Business;
import org.shop.api.common.error.ErrorCode;
import org.shop.api.common.error.ServerErrorCode;
import org.shop.api.common.exception.ApiException;
import org.shop.api.domain.token.business.TokenBusiness;
import org.shop.api.domain.token.controller.model.TokenResponse;
import org.shop.api.domain.user.controller.model.UserLoginRequest;
import org.shop.api.domain.user.controller.model.UserRegisterRequest;
import org.shop.api.domain.user.controller.model.UserResponse;
import org.shop.api.domain.user.converter.UserConverter;
import org.shop.api.domain.user.model.User;
import org.shop.api.domain.user.service.UserService;

import java.util.Optional;

@RequiredArgsConstructor
@Business
public class UserBusiness {

    private final UserService userService;
    private final UserConverter userConverter;

    private final TokenBusiness tokenBusiness;

    /**
     * 사용자에 대한 가입처리 로직
     * 1. request -> entity
     * 2. entity -> save
     * 3. save Entity -> response
     * 4. response return
     */
    public UserResponse register(UserRegisterRequest request) {



        return Optional.ofNullable(request)
            .map(userConverter::toEntity)
            .map(userService::register)
            .map(userConverter::toResponse)
            .orElseThrow(() -> new ApiException(ServerErrorCode.NULL_POINT, "request null"));
    }

    /**
     * 1. email, password 를 가지고 사용자 체크
     * 2. user entity 로그인 확인
     * 3. token 생성
     * 4. token response
     */
    public TokenResponse login(UserLoginRequest request) {
        var userEntity = userService.login(request.getEmail(), request.getPassword());
        var tokenResponse = tokenBusiness.issueToken(userEntity);
        return tokenResponse;
    }

    public UserResponse me(
        User user
    ) {
        var userEntity = userService.getUserWithThrow(user.getId());
        var response = userConverter.toResponse(userEntity);
        return response;
    }
}
