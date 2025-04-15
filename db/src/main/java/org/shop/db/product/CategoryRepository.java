package org.shop.db.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {

    // select * from categories where id = ? and is_active = true order by id desc limit 1
    Optional<CategoryEntity> findFirstByIdAndIsActiveTrueOrderByIdDesc(UUID categoryId);

    // select * from categories where parent_category_id is null and is_active = true order by display_order
    List<CategoryEntity> findAllByParentCategoryIdIsNullAndIsActiveTrueOrderByDisplayOrder();

    // select * from categories where parent_category_id = ? and is_active = true order by display_order
    List<CategoryEntity> findAllByParentCategoryIdAndIsActiveTrueOrderByDisplayOrder(UUID parentCategoryId);

    // select * from categories where is_active = true order by display_order
    List<CategoryEntity> findAllByIsActiveTrueOrderByDisplayOrder();

    // select * from categories where name like ? and is_active = true
    List<CategoryEntity> findAllByNameContainingAndIsActiveTrue(String name);

    // select exists(select 1 from categories where parent_category_id = ?)
    boolean existsByParentCategoryId(UUID parentCategoryId);

    // select count(*) from categories where parent_category_id = ? and is_active = true
    long countByParentCategoryIdAndIsActiveTrue(UUID parentCategoryId);
}
