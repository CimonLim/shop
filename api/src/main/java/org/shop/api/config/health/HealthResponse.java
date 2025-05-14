package org.shop.api.config.health;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HealthResponse {
    private final String health = "UP";
}
