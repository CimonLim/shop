package org.shop.db.payment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentStatus {
    PENDING("결제대기"),
    PROCESSING("처리중"),
    COMPLETED("결제완료"),
    FAILED("결제실패"),
    CANCELLED("결제취소"),
    REFUNDED("환불완료");

    private final String description;
}
