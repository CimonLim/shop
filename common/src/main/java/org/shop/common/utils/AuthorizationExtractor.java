package org.shop.common.utils;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.shop.common.api.error.TokenErrorCode;
import org.shop.common.api.exception.ApiException;

@Slf4j
@UtilityClass
public class AuthorizationExtractor {

    private static final String BEARER_TYPE = "Bearer";

    /**
     * Authorization 헤더에서 토큰 추출
     */
    public String extract(String header) {
        if (StringUtils.isBlank(header)) {
            throw new ApiException(TokenErrorCode.AUTHORIZATION_TOKEN_NOT_FOUND);
        }

        if (!header.startsWith(BEARER_TYPE)) {
            throw new ApiException(TokenErrorCode.INVALID_TOKEN);
        }

        String token = extractToken(header);

        if (StringUtils.isBlank(token)) {
            throw new ApiException(TokenErrorCode.AUTHORIZATION_TOKEN_NOT_FOUND);
        }


        return token;
    }

    /**
     * HttpServletRequest에서 Authorization 헤더 추출
     */
    public String extract(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return extract(header);
    }

    private String extractToken(String header) {
        try {
            String token = header.substring(BEARER_TYPE.length()).trim();

            if (!isValidTokenFormat(token)) {
                throw new ApiException(TokenErrorCode.INVALID_TOKEN);
            }

            // 토큰의 일부만 로깅 (보안)
            log.debug("Token extracted: {}...", maskToken(token));
            return sanitizeToken(token);
        }catch(ApiException e) {
            throw e;
        }catch (Exception e) {
            log.error("Token extraction failed", e);
            throw new ApiException(TokenErrorCode.TOKEN_EXCEPTION);
        }
    }

    /**
     * 토큰 형식 검증
     */
    public boolean isValidTokenFormat(String token) {
        return !StringUtils.isBlank(token) &&
                token.matches("^[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.?[A-Za-z0-9-_.+/=]*$");
    }

    /**
     * XSS 방지를 위한 문자 필터링
     */
    private String sanitizeToken(String token) {
        return token.replaceAll("[<>\"']", "");
    }

    /**
     * 토큰 마스킹 처리 (로깅용)
     */
    private String maskToken(String token) {
        if (token.length() <= 10) return "***";
        return token.substring(0, 10) + "...";
    }
}
