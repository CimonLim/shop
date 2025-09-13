package org.shop.admin.config.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "org.shop.db")
@EnableJpaRepositories(basePackages = "org.shop.db")
@EnableJpaAuditing
public class JpaConfig {
}
