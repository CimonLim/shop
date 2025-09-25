package org.shop.admin.domain.adminuser.controller.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminUserLoginRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
