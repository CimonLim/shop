package org.shop.api.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.shop.api.common.api.Api;
import org.shop.api.common.error.ErrorCodeIfs;
import org.shop.api.common.error.InputValidationErrorCode;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MIN_VALUE + 1)
public class ValidExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Api<Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        log.error("❌ Validation error: ", ex);
        // 에러 메시지 구성
        String errorDescription = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> String.format("%s %s", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining(", "));

        // ErrorCodeIfs 인터페이스를 구현한 에러 코드 사용
        ErrorCodeIfs errorCode = InputValidationErrorCode.INVALID_INPUT_VALUE;

        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(
                        Api.ERROR(errorCode, errorDescription)
                );
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Api<Object>> handleDataIntegrityViolation(
            DataIntegrityViolationException ex) {

        String message = ex.getMessage();
        log.error("❌ Database constraint violation: {}",
                message != null ? message.split("\n")[0] : "Unknown error");

        // 🎯 이메일 중복 체크
        if (message != null && message.contains("users_email_key")) {
            String email = extractEmailFromError(message);
            log.warn("⚠️ Duplicate email registration attempt: {}", email);

            ErrorCodeIfs errorCode = InputValidationErrorCode.DUPLICATE_EMAIL;
            return ResponseEntity
                    .status(errorCode.getHttpStatusCode())
                    .body(Api.ERROR(errorCode, "이미 사용 중인 이메일입니다."));
        }

        // 🎯 전화번호 중복 체크
        if (message != null && message.contains("users_phone_key")) {
            log.warn("⚠️ Duplicate phone number registration attempt");

            ErrorCodeIfs errorCode = InputValidationErrorCode.DUPLICATE_PHONE;
            return ResponseEntity
                    .status(errorCode.getHttpStatusCode())
                    .body(Api.ERROR(errorCode, "이미 사용 중인 전화번호입니다."));
        }

        // 🎯 기타 제약조건 위반
        ErrorCodeIfs errorCode = InputValidationErrorCode.CONSTRAINT_VIOLATION;
        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(Api.ERROR(errorCode, "데이터 제약조건을 위반했습니다."));
    }

    /**
     * 에러 메시지에서 이메일 추출
     */
    private String extractEmailFromError(String errorMessage) {
        try {
            // "Detail: (email)=(lsm@naver.com)" 패턴에서 이메일 추출
            Pattern pattern = Pattern.compile("\\(email\\)=\\(([^)]+)\\)");
            Matcher matcher = pattern.matcher(errorMessage);
            return matcher.find() ? matcher.group(1) : "unknown";
        } catch (Exception e) {
            log.debug("Failed to extract email from error message", e);
            return "unknown";
        }
    }



}
