package org.shop.db.product;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.shop.db.BaseEntityLong;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@ToString
@Table(name = "product_options", schema = "app")
public class ProductOptionEntity extends BaseEntityLong {

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "option_name", nullable = false, length = 50)
    private String optionName;

    @Column(name = "option_value", nullable = false, length = 50)
    private String optionValue;

    @Column(name = "additional_price", precision = 10, scale = 2)
    private BigDecimal additionalPrice;

    @Column(name = "stock_quantity")
    @Setter
    private Integer stockQuantity;

    // 재고 증가
    public void increaseStock(int quantity) {
        this.stockQuantity += quantity;
    }

    // 재고 감소
    public boolean decreaseStock(int quantity) {
        if (this.stockQuantity >= quantity) {
            this.stockQuantity -= quantity;
            return true;
        }
        return false;
    }

    // 재고 있는지 확인
    public boolean hasStock(int quantity) {
        return this.stockQuantity >= quantity;
    }
}
