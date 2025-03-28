package org.shop.api.domain.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.api.common.api.Api;
import org.shop.api.domain.token.controller.model.TokenResponse;
import org.shop.api.domain.user.business.UserBusiness;
import org.shop.api.domain.user.controller.model.UserInfoRequest;
import org.shop.api.domain.user.controller.model.UserLoginRequest;
import org.shop.api.domain.user.controller.model.UserRegisterRequest;
import org.shop.api.domain.user.controller.model.UserResponse;
import org.shop.api.domain.user.converter.UserConverter;
import org.shop.api.domain.user.service.UserService;
import org.shop.db.user.UserEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/user")
public class UserOpenApiController {

    private final UserBusiness userBusiness;
    private final UserService userService;
    private final UserConverter userConverter;


    //사용자 가입 요청
    @PostMapping("/register")
    public Api<UserResponse> register(
        @Valid
        @RequestBody UserRegisterRequest request
    ){
        var response = userBusiness.register(request);
        return Api.OK(response);
    }

//    @PostMapping("/register")
//    public void register(
//            @Valid
//            @RequestBody UserRegisterRequest request
//    ){
//        log.info(request.toString());
//    }

    @GetMapping("/info")
    public Api<UserResponse> info(
            @Valid
            @RequestParam UUID id
    ){
        UserEntity userEntity = userService.getUserWithThrow(id);
        UserResponse response = userConverter.toResponse(userEntity);
        return Api.OK(response);
    }


    // 로그인
    @PostMapping("/login")
    public Api<TokenResponse> login(
        @Valid
        @RequestBody Api<UserLoginRequest> request
    ){
        var response = userBusiness.login(request.getBody());
        return Api.OK(response);
    }

}
