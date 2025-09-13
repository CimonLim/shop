package org.shop.api.domain.user.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.api.common.annotation.Business;
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
import org.shop.db.user.UserEntity;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Business
public class UserBusiness {

    private final UserService userService;

    private final TokenBusiness tokenBusiness;


    public TokenResponse register(UserRegisterRequest request) {

        UserEntity userEntity;
        userEntity = Optional.ofNullable(request)
            .map(UserConverter::registerRequestToEntity)
            .map(userService::register)
            .orElseThrow(() -> new ApiException(ServerErrorCode.NULL_POINT, "UserRegisterRequest null"));

        return tokenBusiness.issueToken(userEntity);
    }


    public TokenResponse login(UserLoginRequest request) {
        UserEntity userEntity;
        userEntity = userService.login(request);
        return tokenBusiness.issueToken(userEntity);
    }

    public UserResponse me(
        User user
    ) {
        var userEntity = userService.getUserWithThrow(user.getId());
        return UserConverter.entityToResponse(userEntity);
    }
}
