package org.shop.api.domain.token.service;

import lombok.RequiredArgsConstructor;
import org.shop.api.common.error.ServerErrorCode;
import org.shop.api.common.exception.ApiException;
import org.shop.api.domain.token.ifs.TokenHelperIfs;
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

    public TokenDto issueAccessToken(UUID userId){
        var data = new HashMap<String, Object>();
        data.put("userId", userId);
        return tokenHelperIfs.issueAccessToken(data);
    }

    public TokenDto issueRefreshToken(UUID userId){
        var data = new HashMap<String, Object>();
        data.put("userId", userId);
        return tokenHelperIfs.issueRefreshToken(data);
    }

    public UUID validationToken(String token) {
        var map = tokenHelperIfs.validationTokenWithThrow(token);

        var userId = map.get("userId");
        Objects.requireNonNull(userId, () -> {
            throw new ApiException(ServerErrorCode.NULL_POINT);
        });

        return UUID.fromString(userId.toString());
    }


}
