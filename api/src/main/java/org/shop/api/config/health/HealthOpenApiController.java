package org.shop.api.config.health;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.api.common.api.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/open-api")
public class HealthOpenApiController {

    @Operation(security = { })
    @GetMapping("/health")
    public Api<HealthResponse> health(){

        return Api.OK(HealthResponse.builder().build());
    }
}
