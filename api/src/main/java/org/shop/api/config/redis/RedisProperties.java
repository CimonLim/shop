package org.shop.api.config.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.data.redis")
public class RedisProperties {
    private String host;
    private int port;
    private String password;
    private int database;
    private int timeout;
    private Lettuce lettuce = new Lettuce();

    @Getter @Setter
    public static class Lettuce {
        private Pool pool = new Pool();
    }

    @Getter @Setter
    public static class Pool {
        private int maxActive;
        private int maxIdle;
        private int minIdle;
        private long maxWait;
    }
}
