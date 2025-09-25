package org.shop.common.api.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum InputValidationErrorCode implements ErrorCodeIfs{

    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST.value(), 2201, "유효하지 않은 입력값"),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST.value(), 2202, "유효하지 않은 타입"),
    MISSING_PARAMETER(HttpStatus.BAD_REQUEST.value(), 2203, "필수 파라미터 누락"),

    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST.value(), 2204, "이미 사용 중인 이메일입니다"),
    DUPLICATE_PHONE(HttpStatus.BAD_REQUEST.value(), 2205, "이미 사용 중인 전화번호입니다"),
    CONSTRAINT_VIOLATION(HttpStatus.BAD_REQUEST.value(), 2206, "데이터 제약조건을 위반했습니다")

    ;

    private final Integer httpStatusCode;
    private final Integer errorCodeValue;
    private final String description;
}
