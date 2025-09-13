package org.shop.admin.config.security;

import lombok.RequiredArgsConstructor;
import org.casbin.jcasbin.main.Enforcer;
import org.shop.admin.config.security.permission.CasbinPermissionEvaluator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class MethodSecurityConfig {

    private final Enforcer enforcer;

    @Bean
    public PermissionEvaluator permissionEvaluator() {
        return new CasbinPermissionEvaluator(enforcer);
    }

    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
        DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
        handler.setPermissionEvaluator(permissionEvaluator());
        return handler;
    }
}