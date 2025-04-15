package org.shop.db.order;

import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;
import org.shop.db.BaseEntityLong;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@ToString
@Table(name = "order_items", schema = "app")
public class OrderItemEntity extends BaseEntityLong {

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "product_id")
    private UUID productId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;
}
