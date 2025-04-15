package org.shop.db.payment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentMethod {
    CREDIT_CARD("신용카드"),
    DEBIT_CARD("체크카드"),
    BANK_TRANSFER("계좌이체"),
    VIRTUAL_ACCOUNT("가상계좌"),
    MOBILE_PAYMENT("모바일결제"),
    POINT("포인트"),
    KAKAO_PAY("카카오페이"),
    NAVER_PAY("네이버페이");

    private final String description;
}
