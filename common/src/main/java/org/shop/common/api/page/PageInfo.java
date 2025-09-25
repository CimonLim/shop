package org.shop.common.api.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageInfo {

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