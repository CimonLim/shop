package org.shop.api.domain.user.converter;

import lombok.experimental.UtilityClass;
import org.shop.api.domain.user.controller.model.UserRegisterRequest;
import org.shop.api.domain.user.controller.model.UserResponse;
import org.shop.common.api.error.ServerErrorCode;
import org.shop.common.api.exception.ApiException;
import org.shop.db.user.UserEntity;

import java.util.Optional;

@UtilityClass
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


