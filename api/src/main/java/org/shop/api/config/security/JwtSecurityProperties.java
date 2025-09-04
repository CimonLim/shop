package org.shop.api.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "app.security.jwt")
@Data
@Component
public class JwtSecurityProperties {
    private List<String> publicPaths = new ArrayList<>();
    private List<String> adminPaths = new ArrayList<>();
}