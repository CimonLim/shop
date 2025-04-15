package org.shop.db.shipping;

import org.shop.db.shipping.enums.ShippingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShippingInfoRepository extends JpaRepository<ShippingInfoEntity, UUID> {

    // select * from shipping_info where order_id = ?
    Optional<ShippingInfoEntity> findByOrderId(UUID orderId);

    // select * from shipping_info where tracking_number = ?
    Optional<ShippingInfoEntity> findByTrackingNumber(String trackingNumber);

    // select * from shipping_info where shipping_status = ? order by shipping_date desc
    List<ShippingInfoEntity> findAllByShippingStatusOrderByShippingDateDesc(ShippingStatus status);

    // select * from shipping_info where recipient_name = ?
    List<ShippingInfoEntity> findAllByRecipientName(String recipientName);

    // select * from shipping_info where shipping_date between ? and ?
    List<ShippingInfoEntity> findAllByShippingDateBetween(
            LocalDateTime startDate, LocalDateTime endDate);

    // select * from shipping_info where shipping_status = ? and shipping_date between ? and ?
    List<ShippingInfoEntity> findAllByShippingStatusAndShippingDateBetween(
            ShippingStatus status, LocalDateTime startDate, LocalDateTime endDate);

    // select * from shipping_info where shipping_status in (?) order by shipping_date desc
    List<ShippingInfoEntity> findAllByShippingStatusInOrderByShippingDateDesc(List<ShippingStatus> statuses);

    // select exists(select 1 from shipping_info where order_id = ? and shipping_status = ?)
    boolean existsByOrderIdAndShippingStatus(UUID orderId, ShippingStatus status);

    // select count(*) from shipping_info where shipping_status = ? and shipping_date between ? and ?
    long countByShippingStatusAndShippingDateBetween(
            ShippingStatus status, LocalDateTime startDate, LocalDateTime endDate);

    // select * from shipping_info where phone_number like ?
    List<ShippingInfoEntity> findAllByPhoneNumberContaining(String phoneNumber);

    // select * from shipping_info where address like ?
    List<ShippingInfoEntity> findAllByAddressContaining(String address);
}
