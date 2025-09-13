package org.shop.admin.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode implements ErrorCodeIfs {

    OK(200 , 200 , "성공"),

    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), 400, "잘못된 요청"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), 401, "인증되지 않은 접근"),
    FORBIDDEN(HttpStatus.FORBIDDEN.value(), 403, "접근 권한 없음"),
    NOT_FOUND(HttpStatus.NOT_FOUND.value(), 404, "리소스를 찾을 수 없음"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED.value(), 405, "지원하지 않는 HTTP 메서드"),
    CONFLICT(HttpStatus.CONFLICT.value(), 409, "리소스 충돌"),
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), 415, "지원하지 않는 미디어 타입"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST.value(), 420, "유효성 검증 실패"),

    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;

}
