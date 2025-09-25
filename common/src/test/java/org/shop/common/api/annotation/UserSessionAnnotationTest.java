package org.shop.common.api.annotation;

import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserSessionAnnotationTest {

    @Test
    void shouldBeApplicableToParameters() {
        // 어노테이션의 @Target 확인
        ElementType[] elementTypes = UserSession.class.getAnnotation(Target.class).value();
        assertEquals(1, elementTypes.length, "UserSession 어노테이션은 하나의 대상만 가져야 합니다");
        assertEquals(ElementType.PARAMETER, elementTypes[0], "UserSession 어노테이션은 파라미터에만 적용되어야 합니다");
    }

    @Test
    void shouldHaveRuntimeRetention() {
        // 어노테이션의 @Retention 확인
        assertEquals(RetentionPolicy.RUNTIME,
                UserSession.class.getAnnotation(Retention.class).value(),
                "UserSession 어노테이션은 RUNTIME 보존 정책을 가져야 합니다");
    }

    @Test
    void shouldBeAccessibleAtRuntime() throws NoSuchMethodException {
        // 테스트 메서드에서 어노테이션 접근 가능 확인
        Method method = TestController.class.getDeclaredMethod("testMethod", Object.class);
        Parameter parameter = method.getParameters()[0];

        assertTrue(parameter.isAnnotationPresent(UserSession.class),
                "UserSession 어노테이션이 파라미터에 존재해야 합니다");
    }

    // 테스트를 위한 더미 컨트롤러 클래스
    static class TestController {
        public void testMethod(@UserSession Object userSession) {
            // 테스트용 메서드
        }
    }
}
