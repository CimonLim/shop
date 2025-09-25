package org.shop.admin.domain.adminuser.converter;

import lombok.experimental.UtilityClass;
import org.shop.admin.domain.adminuser.controller.model.AdminUserListRequest;
import org.shop.admin.domain.adminuser.controller.model.AdminUserListResponse;
import org.shop.admin.domain.adminuser.controller.model.AdminUserResponse;
import org.shop.common.api.error.ServerErrorCode;
import org.shop.common.api.exception.ApiException;
import org.shop.common.api.page.PageInfo;
import org.shop.common.api.page.PageMapper;
import org.shop.db.adminuser.AdminUserEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@UtilityClass
public class AdminUserConverter {

    /**
     * AdminUserEntity List를 AdminUserResponse List로 변환
     */
    public static List<AdminUserResponse> toResponseList(List<AdminUserEntity> adminUserEntityList) {
        return Optional.ofNullable(adminUserEntityList)
                .map(list -> list.stream()
                .map(AdminUserMapper.INSTANCE::entityToResponse)
                .toList())
                .orElseThrow(() -> new ApiException(ServerErrorCode.NULL_POINT, "AdminUserEntity List is null"));
    }

    /**
     * Page<AdminUserEntity>를 AdminUserListResponse로 변환
     */
    public static AdminUserListResponse toListResponse(Page<AdminUserEntity> adminUserPage, AdminUserListRequest request) {
        return Optional.ofNullable(adminUserPage)
                .map(page -> {
                    // 사용자 목록 변환
                    List<AdminUserResponse> adminUserResponses = toResponseList(page.getContent());

                    // 페이징 정보 생성
                    PageInfo pageInfo = PageMapper.INSTANCE.toPageInfo(page);

                    // 필터 정보 생성
                    AdminUserListResponse.FilterInfo filterInfo = createFilterInfo(request);

                    return AdminUserListResponse.builder()
                            .adminUsers(adminUserResponses)
                            .pageInfo(pageInfo)
                            .filterInfo(filterInfo)
                            .build();
                })
                .orElseThrow(() -> new ApiException(ServerErrorCode.NULL_POINT, "AdminUser Page is null"));
    }

    /**
     * AdminUserListRequest로부터 FilterInfo 생성
     */
    private static AdminUserListResponse.FilterInfo createFilterInfo(AdminUserListRequest request) {
        if (request == null) {
            return AdminUserListResponse.FilterInfo.builder()
                    .appliedFilterCount(0)
                    .build();
        }

        int filterCount = 0;
        AdminUserListResponse.FilterInfo.FilterInfoBuilder builder = AdminUserListResponse.FilterInfo.builder();

        // 이메일 필터
        if (request.hasEmailFilter()) {
            builder.email(request.getEmail());
            filterCount++;
        }

        // 이름 필터
        if (request.hasNameFilter()) {
            builder.name(request.getName());
            filterCount++;
        }

        // 검색 키워드
        if (request.hasSearchKeyword()) {
            builder.searchKeyword(request.getSearchKeyword());
            filterCount++;
        }

        // 날짜 범위
        if (request.hasDateRangeFilter()) {
            builder.createdAtFrom(request.getCreatedAtFrom())
                    .createdAtTo(request.getCreatedAtTo());
            filterCount++;
        }

        // 정렬 정보 (필터 개수에는 포함하지 않음)
        builder.sortBy(request.getSortBy())
                .sortDirection(request.getSortDirection())
                .appliedFilterCount(filterCount);

        return builder.build();
    }

}


