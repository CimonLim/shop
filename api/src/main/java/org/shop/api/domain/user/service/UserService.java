package org.shop.api.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.shop.api.common.error.ErrorCode;
import org.shop.api.common.error.ServerErrorCode;
import org.shop.api.common.error.UserErrorCode;
import org.shop.api.common.exception.ApiException;
import org.shop.db.user.UserEntity;
import org.shop.db.user.UserRepository;
import org.shop.db.user.enums.UserRole;
import org.shop.db.user.enums.UserStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * User 도메인 로직을 처리 하는 서비스
 */
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;


    public UserEntity register(UserEntity userEntity){
        return Optional.ofNullable(userEntity)
            .map(it ->{
                userEntity.setStatus(UserStatus.REGISTERED);
                userEntity.setRole(UserRole.USER);
                return userRepository.save(userEntity);
            })
            .orElseThrow(() -> new ApiException(ServerErrorCode.NULL_POINT, "User Entity Null"));

    }

    public UserEntity login(
        String email,
        String password
    ){
        UserEntity entity;
        entity = getUserWithThrow(email, password);
        return entity;
    }

    public UserEntity getUserWithThrow(
        String email,
        String password
    ){
        return userRepository.findFirstByEmailAndPasswordAndStatusOrderByIdDesc(
            email,
            password,
            UserStatus.REGISTERED
        ).orElseThrow(()-> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }

    public UserEntity getUserWithThrow(
            UUID userId
    ){
        return userRepository.findFirstByIdAndStatusOrderByIdDesc(
            userId,
            UserStatus.REGISTERED
        ).orElseThrow(()-> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }



}
