package org.shop.db.adminUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface AdminUserRepository extends JpaRepository<AdminUserEntity, UUID>, JpaSpecificationExecutor<AdminUserEntity> {

    // select * from admin_users where id = ? and status = ? order by id desc limit 1
    Optional<AdminUserEntity> findFirstByIdAndIsActiveOrderByIdDesc(UUID userId, boolean isActive);
    Optional<AdminUserEntity> findFirstByEmailAndIsActiveOrderByIdDesc(String email, boolean isActive);
    boolean existsByEmail(String email);

}