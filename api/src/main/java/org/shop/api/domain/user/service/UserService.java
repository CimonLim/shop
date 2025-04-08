package org.shop.api.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.api.common.error.ServerErrorCode;
import org.shop.api.common.error.UserErrorCode;
import org.shop.api.common.exception.ApiException;
import org.shop.api.domain.user.controller.model.UserLoginRequest;
import org.shop.db.user.UserEntity;
import org.shop.db.user.UserRepository;
import org.shop.db.user.enums.UserRole;
import org.shop.db.user.enums.UserStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * User 도메인 로직을 처리 하는 서비스
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserEntity register(UserEntity userEntity){
        String encodedPassword = passwordEncoder.encode(userEntity.getPassword());
        return Optional.of(userEntity)
            .map(it ->{
                userEntity.setPassword(encodedPassword);
                userEntity.setStatus(UserStatus.REGISTERED);
                userEntity.setRole(UserRole.USER);

                return userRepository.save(userEntity);
            })
            .orElseThrow(() -> new ApiException(ServerErrorCode.NULL_POINT, "User Entity Null"));

    }

    public UserEntity login(
            UserLoginRequest loginRequest
    ){
        UserEntity entity;
        entity = getUserWithThrow(loginRequest.getEmail(), loginRequest.getPassword());
        return entity;
    }

    public UserEntity getUserWithThrow(
        String email,
        String password
    ){
        UserEntity userEntity;
        userEntity = userRepository.findFirstByEmailAndStatusOrderByIdDesc(
                email,
                UserStatus.REGISTERED
        ).orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND, "User Not Found"));


        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            throw new ApiException(UserErrorCode.INVALID_PASSWORD, "Invalid Password");
        }

        return userEntity;
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
