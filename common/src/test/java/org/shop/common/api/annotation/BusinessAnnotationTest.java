package org.shop.common.api.annotation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
@SpringJUnitConfig(BusinessAnnotationTest.TestConfig.class)
class BusinessAnnotationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void businessAnnotatedClassShouldBeRegisteredAsBean() {
        assertTrue(applicationContext.containsBean("testBusinessService"),
                "Business 어노테이션이 적용된 클래스는 빈으로 등록되어야 합니다");
    }

    @Test
    void businessAnnotatedClassWithCustomNameShouldBeRegistered() {
        assertTrue(applicationContext.containsBean("customName"),
                "Business 어노테이션의 value 값으로 빈이 등록되어야 합니다");
        assertNotNull(applicationContext.getBean("customName"),
                "customName으로 빈을 찾을 수 있어야 합니다");
    }

    @Configuration
    @ComponentScan(basePackageClasses = BusinessAnnotationTest.class)
    static class TestConfig {
    }

}// 테스트 대상 클래스들을 별도의 클래스로 분리
@Business
class TestBusinessService {
    public String doSomething() {
        return "business service";
    }
}

@Business("customName")
class TestNamedBusinessService {
    public String doSomething() {
        return "named business service";
    }
}