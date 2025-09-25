package org.shop.common.api.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum TokenErrorCode implements ErrorCodeIfs{

    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED.value(), 2901, "만료된 토큰"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED.value(), 2902, "유효하지 않은 토큰"),
    TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED.value(), 2903, "알수없는 토큰 에러"),
    AUTHORIZATION_TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), 2904, "인증 헤더 토큰 없음"),
    INVALID_PERMISSION(HttpStatus.FORBIDDEN.value(), 2905, "권한 부족"),

    ;

    private final Integer httpStatusCode;
    private final Integer errorCodeValue;
    private final String description;
}
