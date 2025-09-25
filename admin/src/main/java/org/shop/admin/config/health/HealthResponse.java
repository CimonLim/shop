package org.shop.admin.config.health;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthResponse {
    private String health;
}
