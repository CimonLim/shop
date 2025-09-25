package org.shop.admin.domain.adminuser.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.common.api.exception.ApiException;
import org.shop.admin.domain.adminuser.controller.model.*;
import org.shop.admin.domain.adminuser.converter.AdminUserConverter;
import org.shop.admin.domain.adminuser.converter.AdminUserMapper;
import org.shop.admin.domain.adminuser.model.AdminUser;
import org.shop.admin.domain.adminuser.service.AdminUserService;
import org.shop.admin.domain.token.business.TokenBusiness;
import org.shop.admin.domain.token.controller.model.TokenResponse;
import org.shop.common.api.annotation.Business;
import org.shop.common.api.error.ServerErrorCode;
import org.shop.db.adminuser.AdminUserEntity;
import org.springframework.data.domain.Page;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Business
public class AdminUserBusiness {

    private final AdminUserService adminUserService;

    private final TokenBusiness tokenBusiness;

    private final AdminUserMapper adminUserMapper;

    public TokenResponse register(AdminUserRegisterRequest request) {

        AdminUserEntity adminUserEntity;
        adminUserEntity = Optional.ofNullable(request)
                .map(adminUserMapper::registerRequestToEntity)
                .map(adminUserService::register)
                .orElseThrow(() -> new ApiException(ServerErrorCode.NULL_POINT, "UserRegisterRequest null"));

        return tokenBusiness.issueToken(adminUserEntity);
    }

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
