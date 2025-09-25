package org.shop.admin.domain.adminuser.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.common.api.exception.ApiException;
import org.shop.admin.domain.adminuser.controller.model.AdminUserListRequest;
import org.shop.admin.domain.adminuser.controller.model.AdminUserLoginRequest;
import org.shop.common.api.error.ServerErrorCode;
import org.shop.common.api.error.UserErrorCode;
import org.shop.db.adminuser.AdminUserEntity;
import org.shop.db.adminuser.AdminUserRepository;
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
                predicates.add(createSearchKeywordPredicate(root, criteriaBuilder, request.getSearchKeyword()));
            }

            // 날짜 범위 필터
            if (request.hasDateRangeFilter()) {
                addDateRangePredicates(root, criteriaBuilder, predicates, request);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * 검색 키워드에 대한 Predicate 생성
     */
    private Predicate createSearchKeywordPredicate(Root<AdminUserEntity> root, CriteriaBuilder criteriaBuilder, String keyword) {
        String searchPattern = "%" + keyword + "%";
        Predicate emailLike = criteriaBuilder.like(root.get("email"), searchPattern);
        Predicate nameLike = criteriaBuilder.like(root.get("name"), searchPattern);
        return criteriaBuilder.or(emailLike, nameLike);
    }

    /**
     * 날짜 범위 필터에 대한 Predicate 추가
     */
    private void addDateRangePredicates(Root<AdminUserEntity> root, CriteriaBuilder criteriaBuilder,
                                        List<Predicate> predicates, AdminUserListRequest request) {
        Optional.ofNullable(request.getCreatedAtFrom())
                .filter(date -> !date.trim().isEmpty())
                .map(LocalDateTime::parse)
                .ifPresent(fromDate ->
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), fromDate)));

        Optional.ofNullable(request.getCreatedAtTo())
                .filter(date -> !date.trim().isEmpty())
                .map(LocalDateTime::parse)
                .ifPresent(toDate ->
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), toDate)));
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
