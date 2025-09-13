package org.shop.admin.domain.permission.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.casbin.jcasbin.main.Enforcer;
import org.shop.admin.common.annotation.Business;
import org.shop.admin.common.error.ServerErrorCode;
import org.shop.admin.common.exception.ApiException;


@Slf4j
@RequiredArgsConstructor
@Business
public class PermissionBusiness {
    private final Enforcer enforcer;

    public boolean updateRoles() {
        try{
            enforcer.clearPolicy();
            enforcer.loadPolicy();
            return true;
        }
        catch(Exception e){
            throw new ApiException(ServerErrorCode.SERVER_ERROR);
        }

    }
}
