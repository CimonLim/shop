package org.shop.common.api.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.shop.common.api.error.ErrorCodeIfs;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ApiException 단위 테스트 (Mock 사용)")
class ApiExceptionTest {

    @Mock
    private ErrorCodeIfs mockErrorCode;

    @Test
    @DisplayName("ErrorCodeIfs만으로 ApiException 생성 테스트")
    void createApiExceptionWithErrorCodeOnly() {
        // given
        when(mockErrorCode.getDescription()).thenReturn("Mock 에러 메시지");

        // when
        ApiException exception = new ApiException(mockErrorCode);

        // then
        assertEquals(mockErrorCode, exception.getErrorCodeIfs());
        assertEquals("Mock 에러 메시지", exception.getErrorDescription());
        assertEquals("Mock 에러 메시지", exception.getMessage());
        assertNull(exception.getCause());

        // Mock 상호작용 검증
        verify(mockErrorCode, times(1)).getDescription();
    }

    @Test
    @DisplayName("ErrorCodeIfs와 커스텀 메시지로 ApiException 생성 테스트")
    void createApiExceptionWithErrorCodeAndCustomMessage() {
        // given
        String customMessage = "커스텀 에러 메시지";

        // when
        ApiException exception = new ApiException(mockErrorCode, customMessage);

        // then
        assertEquals(mockErrorCode, exception.getErrorCodeIfs());
        assertEquals(customMessage, exception.getErrorDescription());
        assertEquals(customMessage, exception.getMessage());
        assertNull(exception.getCause());

        // Mock과 상호작용하지 않음을 검증
        verifyNoInteractions(mockErrorCode);
    }

    @Test
    @DisplayName("ErrorCodeIfs와 원인 예외로 ApiException 생성 테스트")
    void createApiExceptionWithErrorCodeAndCause() {
        // given
        RuntimeException cause = new RuntimeException("원인 예외");
        when(mockErrorCode.getDescription()).thenReturn("Mock 에러");

        // when
        ApiException exception = new ApiException(mockErrorCode, cause);

        // then
        assertEquals(mockErrorCode, exception.getErrorCodeIfs());
        assertEquals("Mock 에러", exception.getErrorDescription());
        assertEquals(cause, exception.getCause());

        verify(mockErrorCode, times(1)).getDescription();
    }

    @Test
    @DisplayName("ErrorCodeIfs, 원인 예외, 커스텀 메시지로 ApiException 생성 테스트")
    void createApiExceptionWithAllParameters() {
        // given
        RuntimeException cause = new RuntimeException("원인 예외");
        String customMessage = "모든 파라미터를 포함한 커스텀 메시지";

        // when
        ApiException exception = new ApiException(mockErrorCode, cause, customMessage);

        // then
        assertEquals(mockErrorCode, exception.getErrorCodeIfs());
        assertEquals(customMessage, exception.getErrorDescription());
        assertEquals(cause, exception.getCause());

        // 커스텀 메시지 사용으로 Mock과 상호작용하지 않음
        verifyNoInteractions(mockErrorCode);
    }

    @Test
    @DisplayName("null ErrorCodeIfs로 생성 시 NullPointerException 발생")
    void createApiExceptionWithNullErrorCode() {
        // when & then
        assertThrows(NullPointerException.class, () -> {
            new ApiException(null);
        });
    }

    @Test
    @DisplayName("ApiException이 올바른 타입들을 구현/상속하는지 확인")
    void typeHierarchyCheck() {
        // given
        when(mockErrorCode.getDescription()).thenReturn("테스트");
        ApiException exception = new ApiException(mockErrorCode);

        // when & then
        assertInstanceOf(ApiExceptionIfs.class, exception);
        assertInstanceOf(RuntimeException.class, exception);
        assertInstanceOf(Exception.class, exception);
        assertInstanceOf(Throwable.class, exception);
    }

    @Test
    @DisplayName("예외 체이닝이 올바르게 동작하는지 확인")
    void exceptionChaining() {
        // given
        RuntimeException rootCause = new RuntimeException("루트 원인");
        when(mockErrorCode.getDescription()).thenReturn("체이닝 테스트");

        // when
        ApiException exception = new ApiException(mockErrorCode, rootCause);

        // then
        assertEquals(rootCause, exception.getCause());
        assertEquals("루트 원인", exception.getCause().getMessage());
    }

    @Test
    @DisplayName("스택 트레이스가 올바르게 생성되는지 확인")
    void stackTraceGeneration() {
        // given
        when(mockErrorCode.getDescription()).thenReturn("스택 트레이스 테스트");

        // when
        ApiException exception = new ApiException(mockErrorCode);

        // then
        assertNotNull(exception.getStackTrace());
        assertTrue(exception.getStackTrace().length > 0);

        // 현재 메서드가 스택 트레이스에 포함되어 있는지 확인
        boolean foundCurrentMethod = false;
        for (StackTraceElement element : exception.getStackTrace()) {
            if (element.getMethodName().equals("stackTraceGeneration")) {
                foundCurrentMethod = true;
                break;
            }
        }
        assertTrue(foundCurrentMethod, "현재 메서드가 스택 트레이스에 없습니다");
    }

    @Test
    @DisplayName("toString 메서드가 올바르게 동작하는지 확인")
    void toStringMethod() {
        // given
        String customMessage = "toString 테스트 메시지";

        // when
        ApiException exception = new ApiException(mockErrorCode, customMessage);
        String result = exception.toString();

        // then
        assertNotNull(result);
        assertTrue(result.contains("ApiException"));
        assertTrue(result.contains(customMessage));
    }

    @Test
    @DisplayName("ErrorCodeIfs의 getDescription이 여러 번 호출되지 않는지 확인")
    void errorCodeDescriptionCallOptimization() {
        // given
        when(mockErrorCode.getDescription()).thenReturn("최적화 테스트");

        // when
        ApiException exception = new ApiException(mockErrorCode);

        // 여러 번 접근
        String desc1 = exception.getErrorDescription();
        String desc2 = exception.getErrorDescription();
        String desc3 = exception.getErrorDescription();

        // then
        assertEquals("최적화 테스트", desc1);
        assertEquals("최적화 테스트", desc2);
        assertEquals("최적화 테스트", desc3);

        // getDescription()이 생성자에서 한 번만 호출되었는지 확인
        verify(mockErrorCode, times(1)).getDescription();
    }
}