package org.shop.db.shipping;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.shop.db.BaseEntityUuid;
import org.shop.db.shipping.enums.ShippingStatus;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@ToString
@Table(name = "shipping_info", schema = "app")
public class ShippingInfoEntity extends BaseEntityUuid {

    @Column(name = "order_id", unique = true)
    private UUID orderId;

    @Column(name = "recipient_name", nullable = false, length = 100)
    private String recipientName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String address;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "tracking_number", length = 100)
    @Setter
    private String trackingNumber;

    @Column(name = "shipping_status", length = 50)
    @Enumerated(EnumType.STRING)
    @Setter
    private ShippingStatus shippingStatus;

    @Column(name = "shipping_date")
    @Setter
    private LocalDateTime shippingDate;
}
