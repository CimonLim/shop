package org.shop.admin.filter.exception;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.admin.common.api.Api;
import org.shop.admin.common.error.TokenErrorCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication != null ? authentication.getName() : "anonymous";

        log.warn("권한이 부족한 사용자의 접근 시도: {} {} by user: {}",
                request.getMethod(), request.getRequestURI(), username);

        // 권한 부족 에러 응답
        Api<Object> apiResponse = Api.ERROR(
                TokenErrorCode.INVALID_PERMISSION,
                "해당 리소스에 접근할 권한이 없습니다."
        );

        // HTTP 응답 설정
        response.setStatus(TokenErrorCode.INVALID_PERMISSION.getHttpStatusCode());
        response.setContentType("application/json;charset=UTF-8");

        // JSON 응답 작성
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}