package org.shop.admin.domain.permission.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.casbin.jcasbin.main.Enforcer;
import org.shop.common.api.exception.ApiException;
import org.shop.common.api.annotation.Business;
import org.shop.common.api.error.ServerErrorCode;


@Slf4j
@RequiredArgsConstructor
@Business
public class PermissionBusiness {
    private final Enforcer enforcer;

    public boolean reload() {
        try{
            enforcer.clearPolicy();
            enforcer.loadPolicy();
            return true;
        } catch(Exception e){
            throw new ApiException(ServerErrorCode.SERVER_ERROR);
        }
    }
}
