package org.shop.db.payment;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.shop.db.BaseEntityUuid;
import org.shop.db.payment.enums.PaymentMethod;
import org.shop.db.payment.enums.PaymentStatus;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@ToString
@Table(name = "payments", schema = "app")
public class PaymentEntity extends BaseEntityUuid {

    @Column(name = "order_id", unique = true)
    private UUID orderId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "payment_method", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "payment_status", length = 50)
    @Enumerated(EnumType.STRING)
    @Setter
    private PaymentStatus paymentStatus;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;
}
