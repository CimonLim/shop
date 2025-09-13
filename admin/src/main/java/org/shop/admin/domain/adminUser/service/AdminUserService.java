package org.shop.admin.domain.adminUser.service;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.admin.common.error.ServerErrorCode;
import org.shop.admin.common.error.UserErrorCode;
import org.shop.admin.common.exception.ApiException;
import org.shop.admin.domain.adminUser.controller.model.AdminUserListRequest;
import org.shop.admin.domain.adminUser.controller.model.AdminUserLoginRequest;
import org.shop.db.adminUser.AdminUserEntity;
import org.shop.db.adminUser.AdminUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * AdminUser 도메인 로직을 처리 하는 서비스
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;


    public AdminUserEntity register(AdminUserEntity adminUserEntity){
        String encodedPassword = passwordEncoder.encode(adminUserEntity.getPassword());
        return Optional.of(adminUserEntity)
                .map(it ->{
                    adminUserEntity.setPassword(encodedPassword);

                    return adminUserRepository.save(adminUserEntity);
                })
                .orElseThrow(() -> new ApiException(ServerErrorCode.NULL_POINT, "AdminUser Entity Null"));
    }

    public AdminUserEntity login(
            AdminUserLoginRequest loginRequest
    ){
        AdminUserEntity entity;
        entity = getAdminUserWithThrow(loginRequest.getEmail(), loginRequest.getPassword());
        return entity;
    }

    public AdminUserEntity getAdminUserWithThrow(
        String email,
        String password
    ){
        AdminUserEntity adminUserEntity;
        adminUserEntity = adminUserRepository.findFirstByEmailAndIsActiveOrderByIdDesc(
                email,
                true
        ).orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND, "AdminUser Not Found"));


        if (!passwordEncoder.matches(password, adminUserEntity.getPassword())) {
            throw new ApiException(UserErrorCode.INVALID_PASSWORD, "Invalid Password");
        }

        return adminUserEntity;
    }


    public AdminUserEntity getAdminUserWithThrow(
            UUID adminUserId
    ){
        return adminUserRepository.findFirstByIdAndIsActiveOrderByIdDesc(
                adminUserId,
                true
        ).orElseThrow(()-> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }

    /**
     * 필터 조건에 따른 사용자 목록 조회
     */
    public Page<AdminUserEntity> getAdminUsersWithFilter(AdminUserListRequest request) {
        Specification<AdminUserEntity> spec = createSpecification(request);
        Pageable pageable = createPageable(request);

        return adminUserRepository.findAll(spec, pageable);
    }

    /**
     * JPA Specification 생성
     */
    private Specification<AdminUserEntity> createSpecification(AdminUserListRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 이메일 필터
            if (request.hasEmailFilter()) {
                predicates.add(criteriaBuilder.equal(root.get("email"), request.getEmail()));
            }

            // 이름 필터
            if (request.hasNameFilter()) {
                predicates.add(criteriaBuilder.equal(root.get("name"), request.getName()));
            }

            // 검색 키워드 (이메일 또는 이름에서 LIKE 검색)
            if (request.hasSearchKeyword()) {
                String searchPattern = "%" + request.getSearchKeyword() + "%";
                Predicate emailLike = criteriaBuilder.like(root.get("email"), searchPattern);
                Predicate nameLike = criteriaBuilder.like(root.get("name"), searchPattern);
                predicates.add(criteriaBuilder.or(emailLike, nameLike));
            }

            // 날짜 범위 필터
            if (request.hasDateRangeFilter()) {
                if (request.getCreatedAtFrom() != null && !request.getCreatedAtFrom().trim().isEmpty()) {
                    LocalDateTime fromDate = LocalDateTime.parse(request.getCreatedAtFrom());
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), fromDate));
                }

                if (request.getCreatedAtTo() != null && !request.getCreatedAtTo().trim().isEmpty()) {
                    LocalDateTime toDate = LocalDateTime.parse(request.getCreatedAtTo());
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), toDate));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Pageable 객체 생성
     */
    private Pageable createPageable(AdminUserListRequest request) {
        Sort.Direction direction = request.isAscending() ?
                Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, request.getSortBy());

        return PageRequest.of(request.getPage(), request.getSize(), sort);
    }



}
