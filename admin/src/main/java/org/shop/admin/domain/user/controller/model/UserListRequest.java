package org.shop.admin.domain.user.controller.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shop.db.user.enums.UserStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserListRequest {

    @Min(value = 0, message = "페이지 번호는 0 이상이어야 합니다")
    private Integer page = 0;

    @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다")
    @Max(value = 100, message = "페이지 크기는 100 이하여야 합니다")
    private Integer size = 10;

    @Pattern(regexp = "^(id|email|name|createdAt|updatedAt)$",
            message = "정렬 필드는 id, email, name, createdAt, updatedAt 중 하나여야 합니다")
    private String sortBy = "createdAt";

    @Pattern(regexp = "^(asc|desc)$",
            message = "정렬 방향은 asc 또는 desc여야 합니다")
    private String sortDirection = "desc";

    // 필터링 옵션들
    private UserStatus status;

    private String email;

    private String name;

    // 검색어 (이메일, 이름에서 LIKE 검색)
    private String searchKeyword;

    // 날짜 범위 필터링 (ISO 8601 형식)
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.*$|^$",
            message = "날짜 형식은 ISO 8601 형식이어야 합니다 (예: 2023-12-01T00:00:00)")
    private String createdAtFrom;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.*$|^$",
            message = "날짜 형식은 ISO 8601 형식이어야 합니다 (예: 2023-12-01T23:59:59)")
    private String createdAtTo;

    // Validation 헬퍼 메서드들
    public boolean hasStatusFilter() {
        return status != null;
    }

    public boolean hasEmailFilter() {
        return email != null && !email.trim().isEmpty();
    }

    public boolean hasNameFilter() {
        return name != null && !name.trim().isEmpty();
    }

    public boolean hasSearchKeyword() {
        return searchKeyword != null && !searchKeyword.trim().isEmpty();
    }

    public boolean hasDateRangeFilter() {
        return (createdAtFrom != null && !createdAtFrom.trim().isEmpty()) ||
                (createdAtTo != null && !createdAtTo.trim().isEmpty());
    }

    // 정렬 방향을 boolean으로 변환 (Spring Data JPA Sort용)
    public boolean isAscending() {
        return "asc".equalsIgnoreCase(sortDirection);
    }
}