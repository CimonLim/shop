package org.shop.admin.domain.permission.controller;

import lombok.RequiredArgsConstructor;
import org.shop.common.api.response.Api;
import org.shop.admin.domain.permission.business.PermissionBusiness;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/permission")
public class PermissionApiController {

    private final PermissionBusiness permissionBusiness;

    @PostMapping("/reload")
    public Api<Boolean> reload() {
        return Api.ok(permissionBusiness.reload());
    }


}
