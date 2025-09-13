package org.shop.admin.domain.adminUser.controller.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "관리자 사용자 목록 조회 요청")
public class AdminUserListRequest {

    // ========== 검색 & 통합 필터 ==========
    @Schema(description = "통합 검색어 (이메일, 이름, 부서에서 검색)", example = "김철수")
    private String searchKeyword;

    // ========== 기본 정보 필터 ==========
    @Schema(description = "이메일 필터 (부분 일치)", example = "admin@company.com")
    private String email;

    @Schema(description = "이름 필터 (부분 일치)", example = "김철수")
    private String name;

    @Schema(description = "부서 필터 (부분 일치)", example = "IT")
    private String department;

    @Schema(description = "사번 필터 (부분 일치)", example = "EMP001")
    private String employeeId;

    // ========== 상태 필터 ==========
    @Schema(description = "활성 상태 필터 (null이면 전체)", example = "true")
    private Boolean isActive = true;

    @Schema(description = "계정 잠금 상태 필터", example = "false")
    private Boolean accountLocked = false;

    @Schema(description = "MFA 활성화 상태 필터", example = "true")
    private Boolean mfaEnabled;

    // ========== 페이징 & 정렬 ==========
    @Schema(description = "페이지 번호 (0부터 시작)", defaultValue = "0", example = "0")
    @Min(value = 0, message = "페이지 번호는 0 이상이어야 합니다")
    private Integer page = 0;


    @Schema(description = "페이지 크기", defaultValue = "10", example = "10")
    @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다")
    @Max(value = 100, message = "페이지 크기는 100 이하여야 합니다")
    private Integer size = 10;


    @Schema(description = "정렬 필드", defaultValue = "createdAt",
            allowableValues = {"id", "email", "name", "department", "employeeId", "isActive", "lastLoginAt", "createdAt", "updatedAt"})
    @Pattern(regexp = "^(id|email|name|department|employeeId|isActive|lastLoginAt|createdAt|updatedAt)$",
            message = "정렬 필드는 id, email, name, department, employeeId, isActive, lastLoginAt, createdAt, updatedAt 중 하나여야 합니다")
    private String sortBy = "createdAt";

    @Schema(description = "정렬 방향", defaultValue = "desc", allowableValues = {"asc", "desc"})
    @Pattern(regexp = "^(asc|desc)$", message = "정렬 방향은 asc 또는 desc여야 합니다")
    private String sortDirection = "desc";



    // ========== 날짜 범위 필터 ==========
    @Schema(description = "생성일 시작 범위", example = "2024-01-01T00:00:00")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.*$|^$",
            message = "날짜 형식은 ISO 8601 형식이어야 합니다 (예: 2023-12-01T00:00:00)")
    private String createdAtFrom;

    @Schema(description = "생성일 종료 범위", example = "2024-12-31T23:59:59")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.*$|^$",
            message = "날짜 형식은 ISO 8601 형식이어야 합니다 (예: 2026-12-01T23:59:59)")
    private String createdAtTo;

    @Schema(description = "마지막 로그인 시작 범위", example = "2024-01-01T00:00:00")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.*$|^$",
            message = "날짜 형식은 ISO 8601 형식이어야 합니다")
    private String lastLoginAtFrom;

    @Schema(description = "마지막 로그인 종료 범위", example = "2026-12-31T23:59:59")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.*$|^$",
            message = "날짜 형식은 ISO 8601 형식이어야 합니다")
    private String lastLoginAtTo;


    // Validation 헬퍼 메서드들

    @JsonIgnore
    public boolean hasEmailFilter() {
        return email != null && !email.trim().isEmpty();
    }

    @JsonIgnore
    public boolean hasNameFilter() {
        return name != null && !name.trim().isEmpty();
    }

    @JsonIgnore
    public boolean hasSearchKeyword() {
        return searchKeyword != null && !searchKeyword.trim().isEmpty();
    }

    @JsonIgnore
    public boolean hasDateRangeFilter() {
        return (createdAtFrom != null && !createdAtFrom.trim().isEmpty()) ||
                (createdAtTo != null && !createdAtTo.trim().isEmpty());
    }

    // 정렬 방향을 boolean으로 변환 (Spring Data JPA Sort용)
    @JsonIgnore
    public boolean isAscending() {
        return "asc".equalsIgnoreCase(sortDirection);
    }
}