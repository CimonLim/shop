package org.shop.admin.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Token 의 경우 2000번대 에러코드 사용
 */
@AllArgsConstructor
@Getter
public enum BusinessLogicErrorCode implements ErrorCodeIfs{

    BUSINESS_LOGIC_ERROR(HttpStatus.BAD_REQUEST.value(), 681, "비즈니스 로직 오류"),
    INSUFFICIENT_BALANCE(HttpStatus.BAD_REQUEST.value(), 682, "잔액 부족"),
    ORDER_ALREADY_PROCESSED(HttpStatus.CONFLICT.value(), 683, "이미 처리된 주문"),
    INVALID_STATUS_CHANGE(HttpStatus.BAD_REQUEST.value(), 684, "유효하지 않은 상태 변경");

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
