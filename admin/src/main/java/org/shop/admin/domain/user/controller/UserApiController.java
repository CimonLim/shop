package org.shop.admin.domain.user.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.shop.common.api.response.Api;
import org.shop.admin.domain.user.business.UserBusiness;
import org.shop.admin.domain.user.controller.model.UserListRequest;
import org.shop.admin.domain.user.controller.model.UserListResponse;
import org.shop.admin.domain.user.controller.model.UserResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserApiController {

    private final UserBusiness userBusiness;

    @GetMapping("")
    @PreAuthorize("hasPermission('users','read')")
    public Api<UserResponse> getUser(@Parameter String userId) {
        UserResponse response = userBusiness.getUser(userId);
        return Api.ok(response);
    }


    @GetMapping("list")
    @PreAuthorize("hasPermission('users', 'read')")
    public Api<UserListResponse> getUserList(
            @ParameterObject
            @ModelAttribute
            @Valid
            UserListRequest userListRequest
    ) {
        UserListResponse response = userBusiness.getUserList(userListRequest);
        return Api.ok(response);
    }
}
