package org.shop.api.domain.auth.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.api.common.api.Api;
import org.shop.api.common.utils.AuthorizationExtractor;
import org.shop.api.domain.token.business.TokenBusiness;
import org.shop.api.domain.token.controller.model.TokenResponse;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthApiController {
    private final TokenBusiness tokenBusiness;

    @GetMapping("/token/refresh")
    public Api<TokenResponse> refreshToken(
            @Parameter(hidden = true) @RequestHeader("Authorization") String authorizationHeader
    ) {
        var refreshToken = AuthorizationExtractor.extract(authorizationHeader);
        TokenResponse newTokens = tokenBusiness.refreshToken(refreshToken);
        return Api.OK(newTokens);
    }

}
