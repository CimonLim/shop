package org.shop.admin.common.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 값 저장
     * @param key 키
     * @param value 값
     * @param <T> 값의 타입
     */
    public <T> void setValue(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 만료시간과 함께 값 저장
     * @param key 키
     * @param value 값
     * @param timeout 만료 시간
     * @param timeUnit 시간 단위
     * @param <T> 값의 타입
     */
    public <T> void setValueWithExpiration(String key, T value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 만료시간(초)과 함께 값 저장
     * @param key 키
     * @param value 값
     * @param seconds 만료 시간(초)
     * @param <T> 값의 타입
     */
    public <T> void setValueWithExpiration(String key, T value, long seconds) {
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(seconds));
    }

    /**
     * 특정 만료 일시와 함께 값 저장
     * @param key 키
     * @param value 값
     * @param expirationDateTime 만료 일시
     * @param <T> 값의 타입
     */
    public <T> void setValueWithExpiration(String key, T value, LocalDateTime expirationDateTime) {
        Duration duration = Duration.between(LocalDateTime.now(), expirationDateTime);
        if (duration.isNegative()) {
            throw new IllegalArgumentException("만료 시간은 현재 시간보다 이후여야 합니다.");
        }
        redisTemplate.opsForValue().set(key, value, duration);
    }

    /**
     * 값 조회
     * @param key 키
     * @param classType 반환될 객체의 클래스 타입
     * @return Optional로 감싸진 값
     * @param <T> 반환 타입
     */
    public <T> Optional<T> getValue(String key, Class<T> classType) {
        Object value = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(value)
                .map(classType::cast);
    }

    /**
     * 키의 만료 시간 설정
     * @param key 키
     * @param timeout 만료 시간
     * @param timeUnit 시간 단위
     * @return 성공 여부
     */
    public boolean expire(String key, long timeout, TimeUnit timeUnit) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, timeUnit));
    }

    /**
     * 키의 남은 만료 시간 조회 (초 단위)
     * @param key 키
     * @return 남은 만료 시간(초), 키가 없거나 만료시간이 설정되지 않은 경우 -1
     */
    public long getTimeToLive(String key) {
        return Optional.ofNullable(redisTemplate.getExpire(key, TimeUnit.SECONDS))
                .orElse(-1L);
    }

    /**
     * 값 삭제
     * @param key 키
     * @return 삭제 성공 여부
     */
    public boolean deleteValue(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    /**
     * 키 존재 여부 확인
     * @param key 키
     * @return 존재 여부
     */
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 키의 값 증가
     * @param key 키
     * @return 증가된 값
     */
    public long increment(String key) {
        return Optional.ofNullable(redisTemplate.opsForValue().increment(key))
                .orElse(0L);
    }

    /**
     * 키의 값 감소
     * @param key 키
     * @return 감소된 값
     */
    public long decrement(String key) {
        return Optional.ofNullable(redisTemplate.opsForValue().decrement(key))
                .orElse(0L);
    }
}