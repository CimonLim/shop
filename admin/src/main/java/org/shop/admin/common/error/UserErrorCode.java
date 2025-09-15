package org.shop.admin.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * User의 경우 1000번대 에러코드 사용
 * - 1000~1099: 회원가입 관련 오류
 * - 1100~1199: 로그인 관련 오류
 * - 1400~1499: 조회/검색 관련 오류
 */
@AllArgsConstructor
@Getter
public enum UserErrorCode implements ErrorCodeIfs {

    // 회원가입 관련 오류 (1000~1099)
    INVALID_EMAIL_FORMAT(400, 1000, "잘못된 이메일 형식입니다."),
    EMAIL_ALREADY_EXISTS(400, 1001, "이미 사용 중인 이메일입니다."),
    INVALID_PASSWORD_FORMAT(400, 1002, "비밀번호 형식이 올바르지 않습니다."),
    INVALID_PHONE_NUMBER_FORMAT(400, 1003, "잘못된 전화번호 형식입니다."),
    NICKNAME_ALREADY_EXISTS(400, 1004, "이미 사용 중인 닉네임입니다."),
    REGISTRATION_FAILED(500, 1090, "회원가입 처리 중 오류가 발생했습니다."),

    // 로그인 관련 오류 (1100~1199)
    LOGIN_FAILED(401, 1100, "로그인에 실패했습니다."),
    INVALID_PASSWORD(401, 1101, "잘못된 비밀번호입니다."),
    ACCOUNT_LOCKED(401, 1102, "계정이 잠겼습니다. 관리자에게 문의하세요."),
    ACCOUNT_NOT_VERIFIED(401, 1103, "이메일 인증이 필요합니다."),
    LOGIN_ATTEMPT_EXCEEDED(401, 1104, "로그인 시도 횟수를 초과했습니다."),

    // 조회/검색 관련 오류 (1400~1499)
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 1404, "사용자를 찾을 수 없습니다."),

    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}