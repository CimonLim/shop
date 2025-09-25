package org.shop.common.api.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public enum FileErrorCode implements ErrorCodeIfs{

    FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 2701, "파일 업로드 실패"),
    FILE_DOWNLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 2702, "파일 다운로드 실패"),
    INVALID_FILE_FORMAT(HttpStatus.BAD_REQUEST.value(), 2703, "유효하지 않은 파일 형식"),

    ;

    private final Integer httpStatusCode;
    private final Integer errorCodeValue;
    private final String description;
}
