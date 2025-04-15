package org.shop.db.order;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.shop.db.BaseEntityUuid;
import org.shop.db.order.enums.OrderStatus;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@ToString
@Table(name = "orders", schema = "app")
public class OrderEntity extends BaseEntityUuid {

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    @Setter
    private OrderStatus status;

    @Column(name = "order_date")
    private LocalDateTime orderDate;
}
