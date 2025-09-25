package org.shop.common.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shop.common.api.error.ErrorCode;
import org.shop.common.api.error.ErrorCodeIfs;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {

    private Integer resultCode;
    private String resultMessage;
    private String resultDescription;


    // Static factory methods
    public static Result ok() {
        return Result.builder()
                .resultCode(ErrorCode.OK.getErrorCodeValue())
                .resultMessage(ErrorCode.OK.getDescription())
                .resultDescription("success")
                .build();
    }

    public static Result error(ErrorCodeIfs errorCodeIfs) {
        return Result.builder()
                .resultCode(errorCodeIfs.getErrorCodeValue())
                .resultMessage(errorCodeIfs.getDescription())
                .resultDescription("error")
                .build();
    }

    public static Result error(ErrorCodeIfs errorCodeIfs, Throwable tx) {
        return Result.builder()
                .resultCode(errorCodeIfs.getErrorCodeValue())
                .resultMessage(errorCodeIfs.getDescription())
                .resultDescription(tx.getLocalizedMessage())
                .build();
    }

    public static Result error(ErrorCodeIfs errorCodeIfs, String description) {
        return Result.builder()
                .resultCode(errorCodeIfs.getErrorCodeValue())
                .resultMessage(errorCodeIfs.getDescription())
                .resultDescription(description)
                .build();
    }

}
