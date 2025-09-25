package org.shop.admin.domain.user.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.common.api.annotation.Business;
import org.shop.admin.domain.user.controller.model.UserListRequest;
import org.shop.admin.domain.user.controller.model.UserListResponse;
import org.shop.admin.domain.user.controller.model.UserResponse;
import org.shop.admin.domain.user.converter.UserConverter;
import org.shop.admin.domain.user.converter.UserMapper;
import org.shop.admin.domain.user.service.UserService;
import org.shop.db.user.UserEntity;
import org.springframework.data.domain.Page;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Business
public class UserBusiness {

    private final UserService userService;


    public UserResponse getUser(String userId) {

        var userEntity = userService.getUserWithThrow(UUID.fromString(userId));
        return UserMapper.INSTANCE.entityToResponse(userEntity);
    }



    public UserListResponse getUserList(UserListRequest request) {
        log.info("Getting user list with request: {}", request);

        Page<UserEntity> userPage = userService.getUsersWithFilter(request);

        return UserConverter.toListResponse(userPage, request);
    }


}
