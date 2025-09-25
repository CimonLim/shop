package org.shop.admin.config.objectmapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.shop.common.utils.HTMLCharacterEscapes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper(){
        var objectMapper = new ObjectMapper();

        // 1. 기본 모듈 및 기능 설정
        objectMapper.registerModule(new Jdk8Module());        // JDK 8 이후 클래스(Optional 등) 지원
        objectMapper.registerModule(new JavaTimeModule());    // LocalDate, LocalDateTime 등 시간 관련 클래스 지원
        objectMapper.registerModule(new ParameterNamesModule()); // 생성자 파라미터 이름 유지

        // 2. Deserialization 설정
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);   // 알 수 없는 JSON 필드 무시
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false); // 원시 타입에 null 허용
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true); // 빈 문자열을 null로 처리

        // 3. Serialization 설정
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);  // 빈 객체 직렬화 허용
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);     // 날짜를 타임스탬프가 아닌 ISO-8601 형식으로 직렬화

        // 4. XSS 방지를 위한 HTML 이스케이프 설정
        objectMapper.getFactory().setCharacterEscapes(new HTMLCharacterEscapes());

        // 5. 날짜/시간 포맷 설정
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        // 6. 명명 전략 설정
        objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());  // 스네이크 케이스 사용



        return objectMapper;
    }
}
