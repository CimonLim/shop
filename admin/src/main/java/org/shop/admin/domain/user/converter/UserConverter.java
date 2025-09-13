package org.shop.admin.domain.user.converter;

import org.shop.admin.common.annotation.Converter;
import org.shop.admin.common.error.ServerErrorCode;
import org.shop.admin.common.exception.ApiException;
import org.shop.admin.domain.user.controller.model.UserListRequest;
import org.shop.admin.domain.user.controller.model.UserListResponse;
import org.shop.admin.domain.user.controller.model.UserResponse;
import org.shop.db.user.UserEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Converter
public class UserConverter {



    /**
     * UserEntity List를 UserResponse List로 변환
     */
    public static List<UserResponse> toResponseList(List<UserEntity> userEntityList) {
        return Optional.ofNullable(userEntityList)
                .map(list -> list.stream()
                        .map(UserMapper.INSTANCE::entityToResponse)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new ApiException(ServerErrorCode.NULL_POINT, "UserEntity List is null"));
    }

    /**
     * Page<UserEntity>를 UserListResponse로 변환
     */
    public static UserListResponse toListResponse(Page<UserEntity> userPage, UserListRequest request) {
        return Optional.ofNullable(userPage)
                .map(page -> {
                    // 사용자 목록 변환
                    List<UserResponse> userResponses = toResponseList(page.getContent());

                    // 페이징 정보 생성
                    UserListResponse.PageInfo pageInfo = UserListResponse.PageInfo.builder()
                            .currentPage(page.getNumber())
                            .pageSize(page.getSize())
                            .totalElements(page.getTotalElements())
                            .totalPages(page.getTotalPages())
                            .first(page.isFirst())
                            .last(page.isLast())
                            .hasNext(page.hasNext())
                            .hasPrevious(page.hasPrevious())
                            .numberOfElements(page.getNumberOfElements())
                            .empty(page.isEmpty())
                            .build();

                    // 필터 정보 생성
                    UserListResponse.FilterInfo filterInfo = createFilterInfo(request);

                    return UserListResponse.builder()
                            .users(userResponses)
                            .pageInfo(pageInfo)
                            .filterInfo(filterInfo)
                            .build();
                })
                .orElseThrow(() -> new ApiException(ServerErrorCode.NULL_POINT, "User Page is null"));
    }

    /**
     * UserListRequest로부터 FilterInfo 생성
     */
    private static UserListResponse.FilterInfo createFilterInfo(UserListRequest request) {
        if (request == null) {
            return UserListResponse.FilterInfo.builder()
                    .appliedFilterCount(0)
                    .build();
        }

        int filterCount = 0;
        UserListResponse.FilterInfo.FilterInfoBuilder builder = UserListResponse.FilterInfo.builder();

        // 상태 필터
        if (request.hasStatusFilter()) {
            builder.status(request.getStatus().name());
            filterCount++;
        }

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

    /**
     * 빈 UserListResponse 생성 (검색 결과가 없을 때)
     */
    public static UserListResponse toEmptyListResponse(UserListRequest request) {
        UserListResponse.PageInfo pageInfo = UserListResponse.PageInfo.builder()
                .currentPage(request != null ? request.getPage() : 0)
                .pageSize(request != null ? request.getSize() : 10)
                .totalElements(0L)
                .totalPages(0)
                .first(true)
                .last(true)
                .hasNext(false)
                .hasPrevious(false)
                .numberOfElements(0)
                .empty(true)
                .build();

        UserListResponse.FilterInfo filterInfo = createFilterInfo(request);

        return UserListResponse.builder()
                .users(List.of()) // 빈 리스트
                .pageInfo(pageInfo)
                .filterInfo(filterInfo)
                .build();
    }

    /**
     * UserListRequest를 기반으로 정렬 정보 문자열 생성
     * (로깅이나 디버깅용)
     */
    public static String getSortDescription(UserListRequest request) {
        if (request == null) {
            return "createdAt desc (default)";
        }

        return String.format("%s %s",
                request.getSortBy(),
                request.getSortDirection());
    }
}


