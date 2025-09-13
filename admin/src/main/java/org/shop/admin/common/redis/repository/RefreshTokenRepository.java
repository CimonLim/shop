package org.shop.admin.common.redis.repository;

import lombok.RequiredArgsConstructor;
import org.shop.admin.common.redis.RedisService;
import org.shop.admin.domain.token.model.RefreshTokenDto;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {

    private static final String KEY_PREFIX = "refresh_token:";
    private final RedisService redisService;

    /**
     * RefreshToken 저장
     * @param refreshToken 저장할 RefreshToken 엔티티
     */
    public void save(RefreshTokenDto refreshToken) {
        String key = getKey(refreshToken.getId());
        redisService.setValueWithExpiration(
                key,
                refreshToken,
                refreshToken.getExpiration()
        );
    }

    /**
     * ID로 RefreshToken 조회
     * @param id 사용자 ID
     * @return Optional<RefreshToken>
     */
    public Optional<RefreshTokenDto> findById(String id) {
        return redisService.getValue(getKey(id), RefreshTokenDto.class);
    }

    /**
     * RefreshToken 삭제
     * @param id 사용자 ID
     */
    public void deleteById(String id) {
        redisService.deleteValue(getKey(id));
    }

    /**
     * RefreshToken 존재 여부 확인
     * @param id 사용자 ID
     * @return 존재 여부
     */
    public boolean existsById(String id) {
        return redisService.hasKey(getKey(id));
    }

    /**
     * Redis 키 생성
     * @param id 사용자 ID
     * @return Redis 키
     */
    private String getKey(String id) {
        return KEY_PREFIX + id;
    }
}