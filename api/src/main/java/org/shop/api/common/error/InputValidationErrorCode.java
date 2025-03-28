package org.shop.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Token 의 경우 2000번대 에러코드 사용
 */
@AllArgsConstructor
@Getter
public enum InputValidationErrorCode implements ErrorCodeIfs{

    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST.value(), 631, "유효하지 않은 입력값"),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST.value(), 632, "유효하지 않은 타입"),
    MISSING_PARAMETER(HttpStatus.BAD_REQUEST.value(), 633, "필수 파라미터 누락"),

    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
