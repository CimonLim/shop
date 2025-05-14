package org.shop.api.domain.user.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.shop.api.common.annotation.UserSession;
import org.shop.api.common.api.Api;
import org.shop.api.domain.user.business.UserBusiness;
import org.shop.api.domain.user.controller.model.UserResponse;
import org.shop.api.domain.user.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserApiController {

    private final UserBusiness userBusiness;

    @GetMapping("/me")
    public Api<UserResponse> me(
        @Parameter(hidden = true) @UserSession User user
    ){
        var response = userBusiness.me(user);
        return Api.OK(response);
    }
}
