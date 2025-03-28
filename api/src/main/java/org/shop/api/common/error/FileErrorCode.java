package org.shop.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Token 의 경우 2000번대 에러코드 사용
 */
@AllArgsConstructor
@Getter
public enum FileErrorCode implements ErrorCodeIfs{

    FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 641, "파일 업로드 실패"),
    FILE_DOWNLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 642, "파일 다운로드 실패"),
    INVALID_FILE_FORMAT(HttpStatus.BAD_REQUEST.value(), 643, "유효하지 않은 파일 형식"),

    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
