package org.shop.admin.domain.user.controller.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserListResponse {

    // 사용자 목록
    private List<UserResponse> users;

    // 페이징 정보
    private PageInfo pageInfo;

    // 필터링 정보 (어떤 조건으로 검색했는지)
    private FilterInfo filterInfo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PageInfo {

        // 현재 페이지 번호 (0부터 시작)
        private int currentPage;

        // 페이지 크기
        private int pageSize;

        // 총 요소 수
        private long totalElements;

        // 총 페이지 수
        private int totalPages;

        // 첫 번째 페이지 여부
        private boolean first;

        // 마지막 페이지 여부
        private boolean last;

        // 다음 페이지 존재 여부
        private boolean hasNext;

        // 이전 페이지 존재 여부
        private boolean hasPrevious;

        // 현재 페이지의 요소 수
        private int numberOfElements;

        // 빈 페이지 여부
        private boolean empty;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FilterInfo {

        // 적용된 상태 필터
        private String status;

        // 적용된 이메일 필터
        private String email;

        // 적용된 이름 필터
        private String name;

        // 적용된 검색 키워드
        private String searchKeyword;

        // 적용된 날짜 범위
        private String createdAtFrom;
        private String createdAtTo;

        // 정렬 정보
        private String sortBy;
        private String sortDirection;

        // 총 필터 개수 (UI에서 필터 뱃지 표시용)
        private int appliedFilterCount;
    }

    // 편의 메서드들
    @JsonIgnore
    public boolean isEmpty() {
        return users == null || users.isEmpty();
    }

    @JsonIgnore
    public int getUserCount() {
        return users != null ? users.size() : 0;
    }

    @JsonIgnore
    public boolean hasFilters() {
        return filterInfo != null && filterInfo.getAppliedFilterCount() > 0;
    }

    // 페이징 정보 요약 문자열 (예: "1-10 of 100")
    @JsonIgnore
    public String getPageSummary() {
        if (pageInfo == null || isEmpty()) {
            return "0 results";
        }

        long start = (long) pageInfo.getCurrentPage() * pageInfo.getPageSize() + 1;
        long end = Math.min(start + pageInfo.getNumberOfElements() - 1, pageInfo.getTotalElements());

        return String.format("%d-%d of %d", start, end, pageInfo.getTotalElements());
    }

    // 다음 페이지 번호 (없으면 -1)
    @JsonIgnore
    public int getNextPage() {
        return pageInfo != null && pageInfo.isHasNext() ?
                pageInfo.getCurrentPage() + 1 : -1;
    }

    // 이전 페이지 번호 (없으면 -1)
    @JsonIgnore
    public int getPreviousPage() {
        return pageInfo != null && pageInfo.isHasPrevious() ?
                pageInfo.getCurrentPage() - 1 : -1;
    }
}