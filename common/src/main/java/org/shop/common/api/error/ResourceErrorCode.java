package org.shop.common.api.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public enum ResourceErrorCode implements ErrorCodeIfs{

    RESOURCE_ALREADY_EXISTS(HttpStatus.CONFLICT.value(), 2501, "이미 존재하는 리소스"),
    RESOURCE_DELETED(HttpStatus.BAD_REQUEST.value(), 2502, "삭제된 리소스"),
    RESOURCE_LOCKED(HttpStatus.CONFLICT.value(), 2503, "잠긴 리소스"),

    ;

    private final Integer httpStatusCode;
    private final Integer errorCodeValue;
    private final String description;
}
