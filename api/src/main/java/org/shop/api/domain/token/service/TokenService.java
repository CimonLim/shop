package org.shop.api.domain.token.service;

import lombok.RequiredArgsConstructor;
import org.shop.api.common.error.ServerErrorCode;
import org.shop.api.common.error.TokenErrorCode;
import org.shop.api.common.exception.ApiException;
import org.shop.api.common.redis.repository.RefreshTokenRepository;
import org.shop.api.domain.token.ifs.TokenHelperIfs;
import org.shop.api.domain.token.model.RefreshTokenDto;
import org.shop.api.domain.token.model.TokenDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

/**
 * token 에 대한 도메인로직
 */
@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenHelperIfs tokenHelperIfs;
    private final RefreshTokenRepository refreshTokenRepository;
//    private final TokenRepository tokenRepository;

    public TokenDto issueAccessToken(UUID userId){
        var data = new HashMap<String, Object>();
        data.put("userId", userId);
        return tokenHelperIfs.issueAccessToken(data);
    }

    public TokenDto issueRefreshToken(UUID userId){
        var data = new HashMap<String, Object>();
        data.put("userId", userId);
        TokenDto tokenDto = tokenHelperIfs.issueRefreshToken(data);

        saveRefreshTokenToRedis(userId, tokenDto);
        return tokenDto;
    }

    public UUID validationToken(String token) {
        var map = tokenHelperIfs.validationTokenWithThrow(token);

        var userId = map.get("userId");
        Objects.requireNonNull(userId, () -> {
            throw new ApiException(ServerErrorCode.NULL_POINT);
        });

        return UUID.fromString(userId.toString());
    }

    public RefreshTokenDto validationRefreshToken(String refreshToken) {

        var map = tokenHelperIfs.validationTokenWithThrow(refreshToken);
        var userId = map.get("userId");

        RefreshTokenDto storedRefreshToken = refreshTokenRepository.findById(userId.toString())
                .orElseThrow(() -> new ApiException(TokenErrorCode.TOKEN_EXPIRED));

        if (!refreshToken.equals(storedRefreshToken.getRefreshToken())) {
            throw new ApiException(TokenErrorCode.TOKEN_EXCEPTION);
        }

        return storedRefreshToken;
    }

    public UUID deleteRefreshTokenFromRedis(RefreshTokenDto refreshTokenDto) {
        refreshTokenRepository.deleteById(refreshTokenDto.getId());
        return refreshTokenDto.getUserId();
    }

    private void saveRefreshTokenToRedis(UUID userId, TokenDto refreshToken) {
        refreshTokenRepository.save(RefreshTokenDto.builder()
                .id(userId.toString())
                .userId(userId)
                .refreshToken(refreshToken.getToken())
                .expiration(refreshToken.getExpiredAt())
                .build());
    }



//    private void saveRefreshTokenToDB(Long userId, String refreshToken) {
//        TokenEntity token = TokenEntity.builder()
//                .userId(userId)
//                .refreshToken(refreshToken)
//                .expiryDate(LocalDateTime.now().plusSeconds(
//                        jwtTokenHelper.getRefreshTokenValidityInSeconds()))
//                .build();
//        tokenRepository.save(token);
//    }


}
