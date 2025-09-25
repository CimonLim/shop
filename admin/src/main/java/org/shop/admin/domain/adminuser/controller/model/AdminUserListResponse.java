package org.shop.admin.domain.adminuser.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shop.common.api.page.PageInfo;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminUserListResponse {

    // 사용자 목록
    private List<AdminUserResponse> adminUsers;

    // 페이징 정보
    private PageInfo pageInfo;

    // 필터링 정보 (어떤 조건으로 검색했는지)
    private FilterInfo filterInfo;


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
}