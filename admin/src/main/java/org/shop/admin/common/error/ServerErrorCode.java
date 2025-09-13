package org.shop.admin.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Token 의 경우 2000번대 에러코드 사용
 */
@AllArgsConstructor
@Getter
public enum ServerErrorCode implements ErrorCodeIfs{

    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 500, "서버 에러"),
    NOT_IMPLEMENTED(HttpStatus.NOT_IMPLEMENTED.value(), 501, "구현되지 않은 기능"),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE.value(), 503, "서비스 일시적 사용 불가"),
    NULL_POINT(HttpStatus.INTERNAL_SERVER_ERROR.value(), 511, "Null Point 에러"),
    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
