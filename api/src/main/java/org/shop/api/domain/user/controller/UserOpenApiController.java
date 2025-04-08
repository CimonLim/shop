package org.shop.api.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.api.common.api.Api;
import org.shop.api.domain.token.controller.model.TokenResponse;
import org.shop.api.domain.user.business.UserBusiness;
import org.shop.api.domain.user.controller.model.UserLoginRequest;
import org.shop.api.domain.user.controller.model.UserRegisterRequest;
import org.shop.api.domain.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/user")
public class UserOpenApiController {

    private final UserBusiness userBusiness;
    private final UserService userService;


    //사용자 가입 요청
    @Operation(security = { })
    @PostMapping("/register")
    public Api<TokenResponse> register(
        @Valid
        @RequestBody UserRegisterRequest request
    ){
            TokenResponse response = userBusiness.register(request);
            return Api.OK(response);
    }



    // 로그인
    @Operation(security = { })
    @PostMapping("/login")
    public Api<TokenResponse> login(
        @Valid
        @RequestBody UserLoginRequest request
    ){
        TokenResponse response = userBusiness.login(request);
        return Api.OK(response);
    }

}
