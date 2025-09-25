package org.shop.api.filter.exception;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.api.filter.JwtAuthenticationFilter;
import org.shop.common.api.error.TokenErrorCode;
import org.shop.common.api.response.Api;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        log.warn("인증되지 않은 사용자의 접근 시도: {} {}", request.getMethod(), request.getRequestURI());

        // Filter에서 설정한 TokenErrorCode 가져오기
        TokenErrorCode tokenErrorCode = (TokenErrorCode) request.getAttribute(
                JwtAuthenticationFilter.TOKEN_ERROR_ATTRIBUTE);

        // 기본값 설정 (Filter를 거치지 않은 경우)
        if (tokenErrorCode == null) {
            tokenErrorCode = TokenErrorCode.AUTHORIZATION_TOKEN_NOT_FOUND;
        }

        log.info("토큰 에러: {} - {}", tokenErrorCode.getErrorCodeValue(), tokenErrorCode.getDescription());

        // 기존 Api.ERROR() 구조 사용
        Api<Object> apiResponse = Api.error(tokenErrorCode, tokenErrorCode.getDescription());

        // HTTP 응답 설정
        response.setStatus(tokenErrorCode.getHttpStatusCode());
        response.setContentType("application/json;charset=UTF-8");

        // JSON 응답 작성
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}