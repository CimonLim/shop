package org.shop.admin.domain.adminUser.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.shop.admin.common.annotation.UserSession;
import org.shop.admin.common.api.Api;
import org.shop.admin.domain.adminUser.business.AdminUserBusiness;
import org.shop.admin.domain.adminUser.controller.model.AdminUserListRequest;
import org.shop.admin.domain.adminUser.controller.model.AdminUserListResponse;
import org.shop.admin.domain.adminUser.controller.model.AdminUserResponse;
import org.shop.admin.domain.adminUser.model.AdminUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/adminUser")
public class AdminUserApiController {

    private final AdminUserBusiness adminUserBusiness;

    @GetMapping("/me")
    public Api<AdminUserResponse> me(
            @Parameter(hidden = true) @UserSession AdminUser user
    ){
        var response = adminUserBusiness.me(user);
        return Api.OK(response);
    }


    @PostMapping("list")
    @PreAuthorize("hasPermission('admin_user', 'read')")
    public Api<AdminUserListResponse> getAdminUserList(
            @RequestBody
            @Valid
            AdminUserListRequest request
    ) {
        AdminUserListResponse response = adminUserBusiness.getAdminUserList(request);
        return Api.OK(response);
    }
}
