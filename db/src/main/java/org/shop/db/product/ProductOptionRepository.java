package org.shop.db.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductOptionRepository extends JpaRepository<ProductOptionEntity, Long> {

    // 상품의 모든 옵션 조회
    List<ProductOptionEntity> findAllByProductId(UUID productId);

    // 상품의 특정 옵션 조회
    Optional<ProductOptionEntity> findByProductIdAndOptionNameAndOptionValue(
            UUID productId, String optionName, String optionValue);

    // 재고가 있는 옵션만 조회
    List<ProductOptionEntity> findAllByProductIdAndStockQuantityGreaterThan(
            UUID productId, int minStock);

    // 재고가 부족한 옵션 조회
    List<ProductOptionEntity> findAllByStockQuantityLessThan(int threshold);

    // 특정 옵션명으로 조회
    List<ProductOptionEntity> findAllByOptionNameContaining(String optionName);

    // 특정 옵션값으로 조회
    List<ProductOptionEntity> findAllByOptionValueContaining(String optionValue);

    // 재고 업데이트 (동시성 제어)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT po FROM ProductOptionEntity po WHERE po.id = :id")
    Optional<ProductOptionEntity> findByIdWithLock(Long id);

    // 상품의 모든 옵션 삭제
    @Modifying
    @Query("DELETE FROM ProductOptionEntity po WHERE po.productId = :productId")
    void deleteAllByProductId(UUID productId);

    // 재고 일괄 업데이트
    @Modifying
    @Query("UPDATE ProductOptionEntity po SET po.stockQuantity = :quantity " +
            "WHERE po.id = :id")
    void updateStockQuantity(Long id, int quantity);

    // 특정 상품의 옵션 존재 여부 확인
    boolean existsByProductIdAndOptionNameAndOptionValue(
            UUID productId, String optionName, String optionValue);

    // 재고가 0인 옵션 수 조회
    long countByProductIdAndStockQuantityEquals(UUID productId, int quantity);

    // 특정 상품의 총 재고 수량 조회
    @Query("SELECT SUM(po.stockQuantity) FROM ProductOptionEntity po " +
            "WHERE po.productId = :productId")
    Integer getTotalStockQuantityByProductId(UUID productId);
}
