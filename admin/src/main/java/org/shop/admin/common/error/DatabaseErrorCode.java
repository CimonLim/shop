package org.shop.admin.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Token 의 경우 2000번대 에러코드 사용
 */
@AllArgsConstructor
@Getter
public enum DatabaseErrorCode implements ErrorCodeIfs{

    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 661, "데이터베이스 오류"),
    DEADLOCK_ERROR(HttpStatus.CONFLICT.value(), 662, "데드락 발생"),

    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
