package org.shop.common.api.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public enum DatabaseErrorCode implements ErrorCodeIfs{

    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 3001, "데이터베이스 오류"),
    DEADLOCK_ERROR(HttpStatus.CONFLICT.value(), 3002, "데드락 발생"),

    ;

    private final Integer httpStatusCode;
    private final Integer errorCodeValue;
    private final String description;
}
