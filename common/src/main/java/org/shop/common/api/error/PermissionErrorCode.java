package org.shop.common.api.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public enum PermissionErrorCode implements ErrorCodeIfs {

    // 권한 거부
    ACCESS_DENIED(HttpStatus.FORBIDDEN.value(), 2401, "접근 권한이 없습니다"),
    // Casbin 권한 거부
    CASBIN_ACCESS_DENIED(HttpStatus.FORBIDDEN.value(), 2402, "리소스 접근 권한이 없습니다"),
    // 역할 부족
    INSUFFICIENT_ROLE(HttpStatus.FORBIDDEN.value(), 2403, "역할 권한이 부족합니다");

    private final Integer httpStatusCode;
    private final Integer errorCodeValue;
    private final String description;
}