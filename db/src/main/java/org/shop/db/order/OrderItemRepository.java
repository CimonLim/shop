package org.shop.db.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {

    // select * from order_items where order_id = ?
    List<OrderItemEntity> findAllByOrderId(UUID orderId);

    // select * from order_items where product_id = ?
    List<OrderItemEntity> findAllByProductId(UUID productId);

    // select * from order_items where order_id = ? and product_id = ?
    List<OrderItemEntity> findAllByOrderIdAndProductId(UUID orderId, UUID productId);

    // select count(*) from order_items where order_id = ?
    long countByOrderId(UUID orderId);

    @Query("SELECT COALESCE(SUM(oi.quantity), 0) FROM OrderItemEntity oi WHERE oi.productId = :productId")
    Integer sumQuantityByProductId(@Param("productId") UUID productId);

    @Query("SELECT COALESCE(SUM(oi.subtotal), 0) FROM OrderItemEntity oi WHERE oi.orderId = :orderId")
    BigDecimal sumSubtotalByOrderId(@Param("orderId") UUID orderId);
}
