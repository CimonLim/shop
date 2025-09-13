package org.shop.admin.domain.user.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.shop.admin.common.api.Api;
import org.shop.admin.domain.user.business.UserBusiness;
import org.shop.admin.domain.user.controller.model.UserListRequest;
import org.shop.admin.domain.user.controller.model.UserListResponse;
import org.shop.admin.domain.user.controller.model.UserResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserApiController {

    private final UserBusiness userBusiness;

    @GetMapping("")
    @PreAuthorize("hasPermission('user','read')")
    public Api<UserResponse> getUser(@Parameter String userId) {
        UserResponse response = userBusiness.getUser(userId);
        return Api.OK(response);
    }


    @GetMapping("list")
    @PreAuthorize("hasPermission('user', 'read')")
    public Api<UserListResponse> getUserList(@ModelAttribute UserListRequest request) {
        UserListResponse response = userBusiness.getUserList(request);
        return Api.OK(response);
    }
}
