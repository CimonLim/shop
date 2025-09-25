package org.shop.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.shop.common.api.error.ErrorCodeIfs;
import org.shop.common.api.error.TokenErrorCode;
import org.shop.common.api.exception.ApiException;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AuthorizationExtractorTest {

    @Nested
    @DisplayName("extract(String header) 메서드 테스트")
    class ExtractFromHeaderTests {

        @Test
        @DisplayName("올바른 Bearer 토큰을 추출할 수 있다")
        void extractValidBearerToken() {
            // Given
            String validHeader = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIn0.dozjgNryP4J3jVmNHl0w5N_XgL0n3I9PlFUP0THsR8U";

            // When
            String token = AuthorizationExtractor.extract(validHeader);

            // Then
            assertEquals("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIn0.dozjgNryP4J3jVmNHl0w5N_XgL0n3I9PlFUP0THsR8U", token);
        }

        @DisplayName("인증 토큰 추출 실패 케이스")
        @ParameterizedTest(name = "{0}")
        @MethodSource("invalidTokenProvider")
        void extractInvalidTokens(String testDescription, String invalidHeader, ErrorCodeIfs expectedErrorCode) {
            // When & Then
            ApiException exception = assertThrows(ApiException.class, () ->
                    AuthorizationExtractor.extract(invalidHeader)
            );
            assertEquals(expectedErrorCode, exception.getErrorCodeIfs());
        }

        private static Stream<Arguments> invalidTokenProvider() {
            return Stream.of(
                    Arguments.of(
                            "헤더가 null이면 예외를 발생시킨다",
                            null,
                            TokenErrorCode.AUTHORIZATION_TOKEN_NOT_FOUND
                    ),
                    Arguments.of(
                            "Bearer로 시작하지 않는 헤더는 예외를 발생시킨다",
                            "Basic dXNlcm5hbWU6cGFzc3dvcmQ=",
                            TokenErrorCode.INVALID_TOKEN
                    ),
                    Arguments.of(
                            "Bearer 뒤에 토큰이 없으면 예외를 발생시킨다",
                            "Bearer ",
                            TokenErrorCode.INVALID_TOKEN
                    ),
                    Arguments.of(
                            "잘못된 형식의 토큰은 예외를 발생시킨다",
                            "Bearer invalid-token-format",
                            TokenErrorCode.INVALID_TOKEN
                    )
            );
        }

        @Test
        @DisplayName("XSS 시도가 포함된 토큰은 거부된다")
        void rejectTokenWithXss() {
            // Given
            String headerWithXss = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI<script>alert('xss')</script>In0.signature";

            // When & Then
            ApiException exception = assertThrows(ApiException.class, () ->
                    AuthorizationExtractor.extract(headerWithXss)
            );

            assertEquals(TokenErrorCode.INVALID_TOKEN, exception.getErrorCodeIfs());
        }
    }

    @Nested
    @DisplayName("extract(HttpServletRequest request) 메서드 테스트")
    class ExtractFromRequestTests {

        @Test
        @DisplayName("HttpServletRequest에서 올바른 토큰을 추출할 수 있다")
        void extractFromRequest() {
            // Given
            HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
            String validHeader = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIn0.dozjgNryP4J3jVmNHl0w5N_XgL0n3I9PlFUP0THsR8U";
            when(mockRequest.getHeader("Authorization")).thenReturn(validHeader);

            // When
            String token = AuthorizationExtractor.extract(mockRequest);

            // Then
            assertEquals("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIn0.dozjgNryP4J3jVmNHl0w5N_XgL0n3I9PlFUP0THsR8U", token);
        }

        @Test
        @DisplayName("HttpServletRequest에 Authorization 헤더가 없으면 예외를 발생시킨다")
        void extractFromRequestWithoutHeader() {
            // Given
            HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
            when(mockRequest.getHeader("Authorization")).thenReturn(null);

            // When & Then
            ApiException exception = assertThrows(ApiException.class, () ->
                    AuthorizationExtractor.extract(mockRequest)
            );

            assertEquals(TokenErrorCode.AUTHORIZATION_TOKEN_NOT_FOUND, exception.getErrorCodeIfs());
        }
    }

    @Nested
    @DisplayName("isValidTokenFormat 메서드 테스트")
    class ValidTokenFormatTests {

        @Test
        @DisplayName("올바른 JWT 형식은 true를 반환한다")
        void validJwtFormat() {
            // Given
            String validToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIn0.dozjgNryP4J3jVmNHl0w5N_XgL0n3I9PlFUP0THsR8U";

            // When & Then
            assertTrue(AuthorizationExtractor.isValidTokenFormat(validToken));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"   ", "invalid-token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"})
        @DisplayName("잘못된 JWT 형식은 false를 반환한다")
        void invalidJwtFormat(String invalidToken) {
            // When & Then
            assertFalse(AuthorizationExtractor.isValidTokenFormat(invalidToken));
        }
    }
}
