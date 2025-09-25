package org.shop.admin.domain.permission.controller;

import lombok.RequiredArgsConstructor;
import org.shop.admin.domain.permission.business.RoleBusiness;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/role")
public class RoleApiController {
    private final RoleBusiness roleBusiness;


}
