package org.shop.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Token 의 경우 2000번대 에러코드 사용
 */
@AllArgsConstructor
@Getter
public enum ResourceErrorCode implements ErrorCodeIfs{

    RESOURCE_ALREADY_EXISTS(HttpStatus.CONFLICT.value(), 621, "이미 존재하는 리소스"),
    RESOURCE_DELETED(HttpStatus.BAD_REQUEST.value(), 622, "삭제된 리소스"),
    RESOURCE_LOCKED(HttpStatus.CONFLICT.value(), 623, "잠긴 리소스"),

    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
