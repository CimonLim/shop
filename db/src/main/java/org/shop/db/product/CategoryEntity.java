package org.shop.db.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.shop.db.BaseEntityUuid;

import java.util.UUID;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@ToString
@Table(name = "categories", schema = "app")
public class CategoryEntity extends BaseEntityUuid {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "parent_category_id")
    private UUID parentCategoryId;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "display_order")
    private Integer displayOrder;
}
