package org.shop.admin.domain.user.service;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.admin.common.error.UserErrorCode;
import org.shop.admin.common.exception.ApiException;
import org.shop.admin.domain.user.controller.model.UserListRequest;
import org.shop.db.user.UserEntity;
import org.shop.db.user.UserRepository;
import org.shop.db.user.enums.UserStatus;
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
import java.util.UUID;

/**
 * User 도메인 로직을 처리 하는 서비스
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    public UserEntity getUserWithThrow(
        String email,
        String password
    ){
        UserEntity userEntity;
        userEntity = userRepository.findFirstByEmailAndStatusOrderByIdDesc(
                email,
                UserStatus.REGISTERED
        ).orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND, "User Not Found"));


        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            throw new ApiException(UserErrorCode.INVALID_PASSWORD, "Invalid Password");
        }

        return userEntity;
    }


    public UserEntity getUserWithThrow(
            UUID userId
    ){
        return userRepository.findFirstByIdAndStatusOrderByIdDesc(
                userId,
                UserStatus.REGISTERED
        ).orElseThrow(()-> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }

    /**
     * 필터 조건에 따른 사용자 목록 조회
     */
    public Page<UserEntity> getUsersWithFilter(UserListRequest request) {
        Specification<UserEntity> spec = createSpecification(request);
        Pageable pageable = createPageable(request);

        return userRepository.findAll(spec, pageable);
    }

    /**
     * JPA Specification 생성
     */
    private Specification<UserEntity> createSpecification(UserListRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 상태 필터
            if (request.hasStatusFilter()) {
                predicates.add(criteriaBuilder.equal(root.get("status"), request.getStatus()));
            }

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
    private Pageable createPageable(UserListRequest request) {
        Sort.Direction direction = request.isAscending() ?
                Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, request.getSortBy());

        return PageRequest.of(request.getPage(), request.getSize(), sort);
    }



}
