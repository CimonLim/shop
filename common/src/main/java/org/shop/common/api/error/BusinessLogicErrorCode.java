package org.shop.common.api.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public enum BusinessLogicErrorCode implements ErrorCodeIfs{

    BUSINESS_LOGIC_ERROR(HttpStatus.BAD_REQUEST.value(), 2001, "비즈니스 로직 오류"),
    INSUFFICIENT_BALANCE(HttpStatus.BAD_REQUEST.value(), 2002, "잔액 부족"),
    ORDER_ALREADY_PROCESSED(HttpStatus.CONFLICT.value(), 2003, "이미 처리된 주문"),
    INVALID_STATUS_CHANGE(HttpStatus.BAD_REQUEST.value(), 2004, "유효하지 않은 상태 변경");

    private final Integer httpStatusCode;
    private final Integer errorCodeValue;
    private final String description;
}
