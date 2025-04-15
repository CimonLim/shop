package org.shop.db.payment;

import org.shop.db.payment.enums.PaymentMethod;
import org.shop.db.payment.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID> {

    // select * from payments where order_id = ?
    Optional<PaymentEntity> findByOrderId(UUID orderId);

    // select * from payments where payment_status = ? order by payment_date desc
    List<PaymentEntity> findAllByPaymentStatusOrderByPaymentDateDesc(PaymentStatus status);

    // select * from payments where payment_method = ? order by payment_date desc
    List<PaymentEntity> findAllByPaymentMethodOrderByPaymentDateDesc(PaymentMethod method);

    // select * from payments where payment_date between ? and ? order by payment_date desc
    List<PaymentEntity> findAllByPaymentDateBetweenOrderByPaymentDateDesc(
            LocalDateTime startDate, LocalDateTime endDate);

    // select * from payments where payment_status = ? and payment_date between ? and ?
    List<PaymentEntity> findAllByPaymentStatusAndPaymentDateBetween(
            PaymentStatus status, LocalDateTime startDate, LocalDateTime endDate);

    // select exists(select 1 from payments where order_id = ? and payment_status = 'COMPLETED')
    boolean existsByOrderIdAndPaymentStatus(UUID orderId, PaymentStatus status);

    // select count(*) from payments where payment_status = ? and payment_date between ? and ?
    long countByPaymentStatusAndPaymentDateBetween(
            PaymentStatus status, LocalDateTime startDate, LocalDateTime endDate);
}
