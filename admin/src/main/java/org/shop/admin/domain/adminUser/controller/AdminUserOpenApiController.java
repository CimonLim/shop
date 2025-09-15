package org.shop.admin.domain.adminUser.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.admin.common.api.Api;
import org.shop.admin.domain.token.controller.model.TokenResponse;
import org.shop.admin.domain.adminUser.business.AdminUserBusiness;
import org.shop.admin.domain.adminUser.controller.model.AdminUserLoginRequest;
import org.shop.admin.domain.adminUser.controller.model.AdminUserRegisterRequest;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/admin/user")
public class AdminUserOpenApiController {

    private final AdminUserBusiness adminUserBusiness;


    //사용자 가입 요청
    @Operation(security = { })
    @PostMapping("/register")
    public Api<TokenResponse> register(
            @Valid
            @RequestBody AdminUserRegisterRequest request
    ){
        TokenResponse response = adminUserBusiness.register(request);
        return Api.OK(response);
    }



    // 로그인
    @Operation(security = { })
    @PostMapping("/login")
    public Api<TokenResponse> login(
            @Valid
            @RequestBody AdminUserLoginRequest request
    ){
        TokenResponse response = adminUserBusiness.login(request);
        return Api.OK(response);
    }

}