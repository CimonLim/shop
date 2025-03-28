package org.shop.api.config.swagger;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public ModelResolver modelResolver(ObjectMapper objectMapper){
        return new ModelResolver(objectMapper);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Documentation")
                        .version("1.0")
                        .description("API 문서"));
    }

//    @Bean
//    public OpenAPI openAPI() {
//        Info info = new Info()
//                .title("Shop API")
//                .version("1.0")
//                .description("Shop API Documentation");
//
//        // JWT 인증 방식을 Swagger에 추가
//        SecurityScheme securityScheme = new SecurityScheme()
//                .type(SecurityScheme.Type.HTTP)
//                .scheme("bearer")
//                .bearerFormat("JWT")
//                .in(SecurityScheme.In.HEADER)
//                .name("Authorization");
//
//        return new OpenAPI()
//                .info(info)
//                .components(new Components()
//                        .addSecuritySchemes("bearer-key", securityScheme));
//    }
}
