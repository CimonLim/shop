package org.shop.api.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.api.common.error.TokenErrorCode;
import org.shop.api.common.exception.ApiException;
import org.shop.api.common.utils.AuthorizationExtractor;
import org.shop.api.config.security.JwtSecurityProperties;
import org.shop.api.domain.token.business.TokenBusiness;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

// ✅ JWT

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenBusiness tokenBusiness;
    private final JwtSecurityProperties jwtSecurityProperties;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();


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
                        Arrays.asList(new SimpleGrantedAuthority("USER"))
                );
                SecurityContextHolder.getContext().setAuthentication(auth);

                // 성공시에만 다음 필터로 진행
                filterChain.doFilter(request, response);
            } else {
                // 토큰은 있지만 유효하지 않은 경우
                throw new ApiException(TokenErrorCode.INVALID_TOKEN);
            }

        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException(TokenErrorCode.TOKEN_EXCEPTION);
        }


    }


}
