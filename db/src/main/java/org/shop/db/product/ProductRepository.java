package org.shop.db.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

    // select * from products where id = ? and is_active = true order by id desc limit 1
    Optional<ProductEntity> findFirstByIdAndIsActiveTrueOrderByIdDesc(UUID productId);

    // select * from products where category_id = ? and is_active = true
    List<ProductEntity> findAllByCategoryIdAndIsActiveTrue(UUID categoryId);

    // select * from products where is_active = true
    List<ProductEntity> findAllByIsActiveTrue();

    // select * from products where stock_quantity > 0 and is_active = true
    List<ProductEntity> findAllByStockQuantityGreaterThanAndIsActiveTrue(Integer quantity);

    // select * from products where price between ? and ? and is_active = true
    List<ProductEntity> findAllByPriceBetweenAndIsActiveTrue(BigDecimal minPrice, BigDecimal maxPrice);

    // select count(*) from products where category_id = ? and is_active = true
    long countByCategoryIdAndIsActiveTrue(UUID categoryId);
}
