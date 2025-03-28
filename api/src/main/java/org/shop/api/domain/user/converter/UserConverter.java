package org.shop.api.domain.user.converter;

import lombok.RequiredArgsConstructor;
import org.shop.api.common.annotation.Converter;
import org.shop.api.common.error.ServerErrorCode;
import org.shop.api.common.exception.ApiException;
import org.shop.api.domain.user.controller.model.UserRegisterRequest;
import org.shop.api.domain.user.controller.model.UserResponse;
import org.shop.db.user.UserEntity;
import org.shop.db.user.enums.UserRole;
import org.shop.db.user.enums.UserStatus;

import java.util.Optional;

@RequiredArgsConstructor
@Converter
public class UserConverter {

    public UserEntity toEntity(UserRegisterRequest request) {
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

    public UserResponse toResponse(UserEntity userEntity) {
        return Optional.ofNullable(userEntity)
                .map(it -> UserResponse.builder()
                        .id(userEntity.getId())
                        .email(userEntity.getEmail())
                        .name(userEntity.getName())
                        .phoneNumber(userEntity.getPhoneNumber())
                        .address(userEntity.getAddress())
                        .role(userEntity.getRole())
                        .status(userEntity.getStatus())
                        .createdAt(userEntity.getCreatedAt())
                        .updatedAt(userEntity.getUpdatedAt())
                        .build())
                .orElseThrow(() -> new ApiException(ServerErrorCode.NULL_POINT, "UserEntity Null"));
    }
}
