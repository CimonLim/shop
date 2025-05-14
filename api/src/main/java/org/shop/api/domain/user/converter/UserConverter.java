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

    public static UserEntity toEntity(UserRegisterRequest request) {
        return Optional.ofNullable(request)
                .map(it -> {
                    return UserEntity.builder()
                            .email(request.getEmail())
                            .password(request.getPassword())
                            .name(request.getName())
                            .phoneNumber(request.getPhoneNumber())
                            .address(request.getAddress())
                            .build();
                })
                .orElseThrow(() -> new ApiException(ServerErrorCode.NULL_POINT, "UserRegisterRequest Null"));
    }

    public static UserResponse toResponse(UserEntity userEntity) {
        return Optional.ofNullable(userEntity)
                .map(it -> UserResponse.builder()
                        .id(userEntity.getId())
                        .email(userEntity.getEmail())
                        .name(userEntity.getName())
                        .role(userEntity.getRole())
                        .status(userEntity.getStatus())
                        .createdAt(userEntity.getCreatedAt())
                        .build())
                .orElseThrow(() -> new ApiException(ServerErrorCode.NULL_POINT, "UserEntity Null"));
    }


}
