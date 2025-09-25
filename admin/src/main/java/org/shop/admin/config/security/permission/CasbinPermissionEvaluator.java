package org.shop.admin.config.security.permission;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.casbin.jcasbin.main.Enforcer;
import org.shop.common.api.error.PermissionErrorCode;
import org.shop.common.api.exception.ApiException;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Slf4j
@Component
@RequiredArgsConstructor
public class CasbinPermissionEvaluator implements PermissionEvaluator {

    private final Enforcer enforcer;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ApiException(PermissionErrorCode.ACCESS_DENIED, "Authentication is null or not authenticated");
        }

        String username = authentication.getName();
        String resource = targetDomainObject != null ? targetDomainObject.toString() : "";
        String action = permission.toString();

        log.debug("Casbin check: user={}, resource={}, action={}",
                username, resource, action);

        try {
            boolean hasPermission = enforcer.enforce(username, resource, action);

            if (!hasPermission) {

                String description = String.format(
                        "Casbin permission denied: resource=%s, action=%s",
                         resource, action
                );

                // 예외 발생 - ApiExceptionHandler에서 처리됨
                throw new ApiException(PermissionErrorCode.CASBIN_ACCESS_DENIED, description);
            }

            log.debug("Casbin permission granted: user={}, resource={}, action={}",
                    username, resource, action);
            return true;

        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);

            throw new ApiException(PermissionErrorCode.CASBIN_ACCESS_DENIED, "Error occurred during CASBIN_ACCESS_DENIED in permission evaluation");
        }
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId,
                                 String targetType, Object permission) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        String username = authentication.getName();
        String resource = targetType + ":" + targetId;
        String action = permission.toString();

        try {
            boolean hasPermission = enforcer.enforce(username, resource, action);

            if (!hasPermission) {

                String description = String.format(
                        "Casbin resource permission denied: user=%s, resourceType=%s, resourceId=%s, action=%s",
                        username, targetType, targetId, action);

                // 예외 발생 - ApiExceptionHandler에서 처리됨
                throw new ApiException(PermissionErrorCode.CASBIN_ACCESS_DENIED, description);
            }

            return true;

        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException(PermissionErrorCode.CASBIN_ACCESS_DENIED, "Error occurred during Casbin permission evaluation");
        }
    }

    /**
     * 예외 발생 없이 권한만 체크하는 메서드 (기존 동작 유지)
     */
    public boolean checkPermissionSilently(Authentication authentication, String resource, String action) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        try {
            return enforcer.enforce(authentication.getName(), resource, action);
        } catch (Exception e) {
            log.error("Silent permission check failed", e);
            return false;
        }
    }

    /**
     * 예외 발생 없이 권한만 체크하는 메서드 (오버로드)
     */
    public boolean checkPermissionSilently(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        String resource = targetDomainObject != null ? targetDomainObject.toString() : "";
        String action = permission.toString();

        return checkPermissionSilently(authentication, resource, action);
    }
}