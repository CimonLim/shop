package org.shop.api.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.shop.api.common.api.Api;
import org.shop.api.common.error.ErrorCodeIfs;
import org.shop.api.common.error.InputValidationErrorCode;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MIN_VALUE + 1)
public class ValidExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Api<Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        log.error("",ex);
        // 에러 메시지 구성
        String errorDescription = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> String.format("%s: %s", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining(", "));

        // ErrorCodeIfs 인터페이스를 구현한 에러 코드 사용
        ErrorCodeIfs errorCode = InputValidationErrorCode.INVALID_INPUT_VALUE;

        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(
                        Api.ERROR(errorCode, errorDescription)
                );
    }

}
