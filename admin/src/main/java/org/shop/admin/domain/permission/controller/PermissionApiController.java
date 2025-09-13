package org.shop.admin.domain.permission.controller;

import lombok.RequiredArgsConstructor;
import org.shop.admin.common.api.Api;
import org.shop.admin.domain.permission.business.PermissionBusiness;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/permission")
public class PermissionApiController {

    private final PermissionBusiness permissionBusiness;

    @GetMapping("/update")
    public Api<String> update() {
        permissionBusiness.updateRoles();

        return Api.OK("success");
    }
//    private final PermissionBusiness permissionBusiness;
//
//    @GetMapping("/check")
//    public Api<>

}
