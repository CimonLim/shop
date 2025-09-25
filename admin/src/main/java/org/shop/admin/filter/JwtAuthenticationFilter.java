package org.shop.admin.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.common.api.exception.ApiException;
import org.shop.common.utils.AuthorizationExtractor;
import org.shop.admin.config.security.JwtSecurityProperties;
import org.shop.admin.domain.token.business.TokenBusiness;
import org.shop.common.api.error.TokenErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenBusiness tokenBusiness;
    private final JwtSecurityProperties jwtSecurityProperties;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public static final String TOKEN_ERROR_ATTRIBUTE = "TOKEN_ERROR_CODE";


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestURI = request.getRequestURI();

        boolean isPublicPath = jwtSecurityProperties.getPublicPaths().stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, requestURI));

        if (isPublicPath) {
            log.debug("Skipping JWT filter for public path: {}", requestURI);
        }

        return isPublicPath;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException, ApiException {
        
        try {
            String token = AuthorizationExtractor.extract(request);
            UUID userId = tokenBusiness.validationAccessToken(token);

            if (userId != null) {
                // SecurityContext에 인증 정보 설정
                Authentication auth = new PreAuthenticatedAuthenticationToken(
                        userId.toString(), null,
                        Collections.emptyList()
                );
                SecurityContextHolder.getContext().setAuthentication(auth);


            } else {
                // 토큰은 있지만 유효하지 않은 경우
                request.setAttribute(TOKEN_ERROR_ATTRIBUTE, TokenErrorCode.INVALID_TOKEN);
            }


        } catch (ApiException e) {
            // TokenBusiness에서 발생한 ApiException 처리
            log.warn("토큰 검증 실패: {}", e.getMessage());

            // ApiException의 ErrorCode를 TokenErrorCode로 변환
            if (e.getErrorCodeIfs() instanceof TokenErrorCode) {
                request.setAttribute(TOKEN_ERROR_ATTRIBUTE, e.getErrorCodeIfs());
            } else {
                // 기타 예외는 일반 토큰 예외로 처리
                request.setAttribute(TOKEN_ERROR_ATTRIBUTE, TokenErrorCode.TOKEN_EXCEPTION);
            }

        } catch (Exception e) {
            // 예상치 못한 예외
            log.error("JWT 토큰 처리 중 예상치 못한 오류 발생", e);
            request.setAttribute(TOKEN_ERROR_ATTRIBUTE, TokenErrorCode.TOKEN_EXCEPTION);
        }

        filterChain.doFilter(request, response);
    }


}
