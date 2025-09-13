package org.shop.api.domain.user.converter;

import org.shop.api.common.annotation.Converter;
import org.shop.api.common.error.ServerErrorCode;
import org.shop.api.common.exception.ApiException;
import org.shop.api.domain.user.controller.model.UserRegisterRequest;
import org.shop.api.domain.user.controller.model.UserResponse;
import org.shop.db.user.UserEntity;

import java.util.Optional;

@Converter
public class UserConverter {

    public static UserEntity registerRequestToEntity(UserRegisterRequest request) {
        return Optional.ofNullable(request)
                .map(UserMapper.INSTANCE::registerRequestToEntity)
                .orElseThrow(() -> new ApiException(ServerErrorCode.NULL_POINT, "UserRegisterRequest Null"));
    }

    public static UserResponse entityToResponse(UserEntity userEntity) {
        return Optional.ofNullable(userEntity)
                .map(UserMapper.INSTANCE::entityToResponse)
                .orElseThrow(() -> new ApiException(ServerErrorCode.NULL_POINT, "UserEntity Null"));
    }

}


