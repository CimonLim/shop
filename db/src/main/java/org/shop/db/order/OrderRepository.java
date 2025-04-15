package org.shop.db.order;

import org.shop.db.order.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

    // select * from orders where id = ? and user_id = ? order by id desc limit 1
    Optional<OrderEntity> findFirstByIdAndUserIdOrderByIdDesc(UUID orderId, UUID userId);

    // select * from orders where user_id = ? order by order_date desc
    List<OrderEntity> findAllByUserIdOrderByOrderDateDesc(UUID userId);

    // select * from orders where status = ? order by order_date desc
    List<OrderEntity> findAllByStatusOrderByOrderDateDesc(OrderStatus status);

    // select * from orders where user_id = ? and status = ? order by order_date desc
    List<OrderEntity> findAllByUserIdAndStatusOrderByOrderDateDesc(UUID userId, OrderStatus status);

    // select * from orders where order_date between ? and ? order by order_date desc
    List<OrderEntity> findAllByOrderDateBetweenOrderByOrderDateDesc(LocalDateTime startDate, LocalDateTime endDate);

    // select count(*) from orders where user_id = ? and status = ?
    long countByUserIdAndStatus(UUID userId, OrderStatus status);
}
