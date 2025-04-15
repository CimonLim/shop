package org.shop.db.shipping.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ShippingStatus {
    PENDING("배송준비중"),
    PROCESSING("처리중"),
    READY("출고대기"),
    SHIPPING("배송중"),
    DELIVERED("배송완료"),
    CANCELLED("배송취소"),
    RETURNED("반송됨");

    private final String description;
}
