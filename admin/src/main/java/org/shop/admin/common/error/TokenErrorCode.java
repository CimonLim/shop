package org.shop.admin.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Token 의 경우 2000번대 에러코드 사용
 */
@AllArgsConstructor
@Getter
public enum TokenErrorCode implements ErrorCodeIfs{

    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED.value(), 611, "만료된 토큰"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED.value(), 612, "유효하지 않은 토큰"),
    TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED.value(), 613, "알수없는 토큰 에러"),
    AUTHORIZATION_TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), 614, "인증 헤더 토큰 없음"),
    INVALID_PERMISSION(HttpStatus.FORBIDDEN.value(), 615, "권한 부족"),

    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
