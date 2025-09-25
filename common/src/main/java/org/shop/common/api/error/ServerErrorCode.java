package org.shop.common.api.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public enum ServerErrorCode implements ErrorCodeIfs{

    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 5000, "서버 에러"),
    NOT_IMPLEMENTED(HttpStatus.NOT_IMPLEMENTED.value(), 5001, "구현되지 않은 기능"),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE.value(), 5003, "서비스 일시적 사용 불가"),
    NULL_POINT(HttpStatus.INTERNAL_SERVER_ERROR.value(), 5011, "Null Point 에러"),
    ;

    private final Integer httpStatusCode;
    private final Integer errorCodeValue;
    private final String description;
}
