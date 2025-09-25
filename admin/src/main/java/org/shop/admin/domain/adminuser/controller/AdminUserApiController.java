package org.shop.admin.domain.adminuser.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.shop.common.api.annotation.UserSession;
import org.shop.common.api.response.Api;
import org.shop.admin.domain.adminuser.business.AdminUserBusiness;
import org.shop.admin.domain.adminuser.controller.model.AdminUserListRequest;
import org.shop.admin.domain.adminuser.controller.model.AdminUserListResponse;
import org.shop.admin.domain.adminuser.controller.model.AdminUserResponse;
import org.shop.admin.domain.adminuser.model.AdminUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/user")
public class AdminUserApiController {

    private final AdminUserBusiness adminUserBusiness;

    @GetMapping("/me")
    public Api<AdminUserResponse> me(
            @Parameter(hidden = true) @UserSession AdminUser user
    ){
        var response = adminUserBusiness.me(user);
        return Api.ok(response);
    }


    @PostMapping("list")
    @PreAuthorize("hasPermission('admin_users', 'read')")
    public Api<AdminUserListResponse> getAdminUserList(
            @RequestBody
            @Valid
            AdminUserListRequest request
    ) {
        AdminUserListResponse response = adminUserBusiness.getAdminUserList(request);
        return Api.ok(response);
    }
}
