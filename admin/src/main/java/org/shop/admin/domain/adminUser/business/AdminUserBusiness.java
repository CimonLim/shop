package org.shop.admin.domain.adminUser.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.admin.common.annotation.Business;
import org.shop.admin.common.error.ServerErrorCode;
import org.shop.admin.common.exception.ApiException;
import org.shop.admin.domain.adminUser.controller.model.*;
import org.shop.admin.domain.adminUser.converter.AdminUserMapper;
import org.shop.admin.domain.adminUser.model.AdminUser;
import org.shop.admin.domain.token.business.TokenBusiness;
import org.shop.admin.domain.adminUser.converter.AdminUserConverter;
import org.shop.admin.domain.adminUser.service.AdminUserService;
import org.shop.admin.domain.token.controller.model.TokenResponse;
import org.shop.db.adminUser.AdminUserEntity;
import org.springframework.data.domain.Page;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Business
public class AdminUserBusiness {

    private final AdminUserService adminUserService;

    private final TokenBusiness tokenBusiness;

    private final AdminUserMapper adminUserMapper;

    /**
     * 사용자에 대한 가입처리 로직
     * 1. request -> entity
     * 2. entity -> save
     * 3. save Entity -> response
     * 4. response return
     */
    public TokenResponse register(AdminUserRegisterRequest request) {

        AdminUserEntity adminUserEntity;
        adminUserEntity = Optional.ofNullable(request)
                .map(adminUserMapper::registerRequestToEntity)
                .map(adminUserService::register)
                .orElseThrow(() -> new ApiException(ServerErrorCode.NULL_POINT, "UserRegisterRequest null"));

        return tokenBusiness.issueToken(adminUserEntity);
    }

    /**
     * 1. email, password 를 가지고 사용자 체크
     * 2. user entity 로그인 확인
     * 3. token 생성
     * 4. token response
     */
    public TokenResponse login(AdminUserLoginRequest request) {
        AdminUserEntity adminUserEntity;
        adminUserEntity = adminUserService.login(request);
        return tokenBusiness.issueToken(adminUserEntity);
    }

    public AdminUserResponse me(
            AdminUser adminUser
    ) {
        var adminUserEntity = adminUserService.getAdminUserWithThrow(adminUser.getId());
        return adminUserMapper.entityToResponse(adminUserEntity);
    }


    public AdminUserListResponse getAdminUserList(AdminUserListRequest request) {
        log.info("Getting adminUser list with request: {}", request);

        Page<AdminUserEntity> adminUserPage = adminUserService.getAdminUsersWithFilter(request);

        return AdminUserConverter.toListResponse(adminUserPage, request);
    }


}
