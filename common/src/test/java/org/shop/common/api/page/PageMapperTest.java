package org.shop.common.api.page;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PageMapper 단위 테스트")
class PageMapperTest {

    private final PageMapper pageMapper = PageMapper.INSTANCE;

    @Nested
    @DisplayName("페이지 번호 매핑 테스트")
    class PageNumberMappingTest {

        @Test
        @DisplayName("0-based 페이지 번호를 그대로 유지한다")
        void testToPageInfo_withZeroBasedPageNumber_shouldKeepZeroBased() {
            // Arrange
            List<String> content = Arrays.asList("item1", "item2", "item3");
            Pageable pageable = PageRequest.of(0, 5); // 0-based 첫 페이지
            Page<String> page = new PageImpl<>(content, pageable, 15);

            // Act
            PageInfo result = pageMapper.toPageInfo(page);

            // Assert
            assertThat(result.getCurrentPage()).isEqualTo(0); // 0-based 유지
            assertThat(result.getPageSize()).isEqualTo(5);
            assertThat(result.getTotalElements()).isEqualTo(15);
            assertThat(result.getTotalPages()).isEqualTo(3);
        }

        @ParameterizedTest(name = "Spring Data 페이지 {0} → UI 페이지 {0}")
        @MethodSource("pageNumberMappingProvider")
        @DisplayName("다양한 페이지 번호를 그대로 매핑한다")
        void testToPageInfo_withVariousPageNumbers_shouldMapDirectly(
                int springDataPageNumber, int expectedUIPageNumber) {
            // Arrange
            List<String> content = Arrays.asList("item1", "item2");
            Pageable pageable = PageRequest.of(springDataPageNumber, 10);
            Page<String> page = new PageImpl<>(content, pageable, 100);

            // Act
            PageInfo result = pageMapper.toPageInfo(page);

            // Assert
            assertThat(result.getCurrentPage()).isEqualTo(expectedUIPageNumber);
        }

        static Stream<Arguments> pageNumberMappingProvider() {
            return Stream.of(
                    Arguments.of(0, 0),   // 첫 페이지
                    Arguments.of(1, 1),   // 두 번째 페이지
                    Arguments.of(4, 4),   // 다섯 번째 페이지
                    Arguments.of(9, 9)    // 열 번째 페이지
            );
        }
    }

    @Nested
    @DisplayName("경계 조건 테스트")
    class BoundaryConditionTest {

        @Test
        @DisplayName("첫 번째 페이지의 모든 속성을 정확히 매핑한다")
        void testToPageInfo_withFirstPage_shouldMapAllPropertiesCorrectly() {
            // Arrange
            List<String> content = Arrays.asList("item1", "item2", "item3");
            Pageable pageable = PageRequest.of(0, 5); // 첫 페이지
            Page<String> page = new PageImpl<>(content, pageable, 15);

            // Act
            PageInfo result = pageMapper.toPageInfo(page);

            // Assert - 모든 속성 검증
            assertAll("첫 번째 페이지 속성 검증",
                    () -> assertThat(result.getCurrentPage()).isEqualTo(0), // 0-based 유지
                    () -> assertThat(result.getPageSize()).isEqualTo(5),
                    () -> assertThat(result.getTotalElements()).isEqualTo(15),
                    () -> assertThat(result.getTotalPages()).isEqualTo(3),
                    () -> assertThat(result.getNumberOfElements()).isEqualTo(3),
                    () -> assertThat(result.isFirst()).isTrue(),
                    () -> assertThat(result.isLast()).isFalse(),
                    () -> assertThat(result.isHasNext()).isTrue(),
                    () -> assertThat(result.isHasPrevious()).isFalse(),
                    () -> assertThat(result.isEmpty()).isFalse()
            );
        }

        @Test
        @DisplayName("마지막 페이지의 모든 속성을 정확히 매핑한다")
        void testToPageInfo_withLastPage_shouldMapAllPropertiesCorrectly() {
            // Arrange
            List<String> content = Arrays.asList("item11", "item12"); // 마지막 페이지 2개 요소
            Pageable pageable = PageRequest.of(2, 5); // 마지막 페이지 (0-based)
            Page<String> page = new PageImpl<>(content, pageable, 12);

            // Act
            PageInfo result = pageMapper.toPageInfo(page);

            // Assert - 모든 속성 검증
            assertAll("마지막 페이지 속성 검증",
                    () -> assertThat(result.getCurrentPage()).isEqualTo(2), // 0-based 유지
                    () -> assertThat(result.getPageSize()).isEqualTo(5),
                    () -> assertThat(result.getTotalElements()).isEqualTo(12),
                    () -> assertThat(result.getTotalPages()).isEqualTo(3),
                    () -> assertThat(result.getNumberOfElements()).isEqualTo(2),
                    () -> assertThat(result.isFirst()).isFalse(),
                    () -> assertThat(result.isLast()).isTrue(),
                    () -> assertThat(result.isHasNext()).isFalse(),
                    () -> assertThat(result.isHasPrevious()).isTrue(),
                    () -> assertThat(result.isEmpty()).isFalse()
            );
        }

        @Test
        @DisplayName("중간 페이지의 모든 속성을 정확히 매핑한다")
        void testToPageInfo_withMiddlePage_shouldMapAllPropertiesCorrectly() {
            // Arrange
            List<String> content = Arrays.asList("item6", "item7", "item8", "item9", "item10");
            Pageable pageable = PageRequest.of(1, 5); // 중간 페이지 (0-based)
            Page<String> page = new PageImpl<>(content, pageable, 15);

            // Act
            PageInfo result = pageMapper.toPageInfo(page);

            // Assert - 모든 속성 검증
            assertAll("중간 페이지 속성 검증",
                    () -> assertThat(result.getCurrentPage()).isEqualTo(1), // 0-based 유지
                    () -> assertThat(result.getPageSize()).isEqualTo(5),
                    () -> assertThat(result.getTotalElements()).isEqualTo(15),
                    () -> assertThat(result.getTotalPages()).isEqualTo(3),
                    () -> assertThat(result.getNumberOfElements()).isEqualTo(5),
                    () -> assertThat(result.isFirst()).isFalse(),
                    () -> assertThat(result.isLast()).isFalse(),
                    () -> assertThat(result.isHasNext()).isTrue(),
                    () -> assertThat(result.isHasPrevious()).isTrue(),
                    () -> assertThat(result.isEmpty()).isFalse()
            );
        }

        @Test
        @DisplayName("빈 페이지를 정확히 매핑한다")
        void testToPageInfo_withEmptyPage_shouldMapCorrectly() {
            // Arrange
            List<String> emptyContent = Collections.emptyList();
            Pageable pageable = PageRequest.of(0, 10);
            Page<String> emptyPage = new PageImpl<>(emptyContent, pageable, 0);

            // Act
            PageInfo result = pageMapper.toPageInfo(emptyPage);

            // Assert
            assertAll("빈 페이지 속성 검증",
                    () -> assertThat(result.getCurrentPage()).isEqualTo(0), // 0-based 유지
                    () -> assertThat(result.getPageSize()).isEqualTo(10),
                    () -> assertThat(result.getTotalElements()).isEqualTo(0),
                    () -> assertThat(result.getTotalPages()).isEqualTo(0),
                    () -> assertThat(result.getNumberOfElements()).isEqualTo(0),
                    () -> assertThat(result.isFirst()).isTrue(),
                    () -> assertThat(result.isLast()).isTrue(),
                    () -> assertThat(result.isHasNext()).isFalse(),
                    () -> assertThat(result.isHasPrevious()).isFalse(),
                    () -> assertThat(result.isEmpty()).isTrue()
            );
        }

        @Test
        @DisplayName("단일 페이지(전체 데이터가 한 페이지)를 정확히 매핑한다")
        void testToPageInfo_withSinglePage_shouldMapCorrectly() {
            // Arrange
            List<String> content = Arrays.asList("item1", "item2", "item3");
            Pageable pageable = PageRequest.of(0, 10); // 페이지 크기가 전체 데이터보다 큼
            Page<String> singlePage = new PageImpl<>(content, pageable, 3);

            // Act
            PageInfo result = pageMapper.toPageInfo(singlePage);

            // Assert
            assertAll("단일 페이지 속성 검증",
                    () -> assertThat(result.getCurrentPage()).isEqualTo(0), // 0-based 유지
                    () -> assertThat(result.getPageSize()).isEqualTo(10),
                    () -> assertThat(result.getTotalElements()).isEqualTo(3),
                    () -> assertThat(result.getTotalPages()).isEqualTo(1),
                    () -> assertThat(result.getNumberOfElements()).isEqualTo(3),
                    () -> assertThat(result.isFirst()).isTrue(),
                    () -> assertThat(result.isLast()).isTrue(),
                    () -> assertThat(result.isHasNext()).isFalse(),
                    () -> assertThat(result.isHasPrevious()).isFalse(),
                    () -> assertThat(result.isEmpty()).isFalse()
            );
        }
    }

    @Nested
    @DisplayName("특수 케이스 테스트")
    class SpecialCaseTest {

        @Test
        @DisplayName("매우 큰 페이지 번호도 정확히 매핑한다")
        void testToPageInfo_withLargePageNumber_shouldMapCorrectly() {
            // Arrange
            List<String> content = Arrays.asList("item1", "item2");
            Pageable pageable = PageRequest.of(999, 10); // 매우 큰 페이지 번호
            Page<String> page = new PageImpl<>(content, pageable, 10000);

            // Act
            PageInfo result = pageMapper.toPageInfo(page);

            // Assert
            assertThat(result.getCurrentPage()).isEqualTo(999); // 0-based 유지
            assertThat(result.getTotalPages()).isEqualTo(1000);
        }

        @Test
        @DisplayName("페이지 크기가 1인 경우도 정확히 처리한다")
        void testToPageInfo_withPageSizeOne_shouldMapCorrectly() {
            // Arrange
            List<String> content = Arrays.asList("single-item");
            Pageable pageable = PageRequest.of(2, 1); // 페이지 크기 1
            Page<String> page = new PageImpl<>(content, pageable, 5);

            // Act
            PageInfo result = pageMapper.toPageInfo(page);

            // Assert
            assertAll("페이지 크기 1 검증",
                    () -> assertThat(result.getCurrentPage()).isEqualTo(2), // 0-based 유지
                    () -> assertThat(result.getPageSize()).isEqualTo(1),
                    () -> assertThat(result.getTotalElements()).isEqualTo(5),
                    () -> assertThat(result.getTotalPages()).isEqualTo(5),
                    () -> assertThat(result.getNumberOfElements()).isEqualTo(1)
            );
        }

        @Test
        @DisplayName("매우 큰 페이지 크기도 정확히 처리한다")
        void testToPageInfo_withLargePageSize_shouldMapCorrectly() {
            // Arrange
            List<String> content = Arrays.asList("item1", "item2", "item3");
            Pageable pageable = PageRequest.of(0, 1000); // 매우 큰 페이지 크기
            Page<String> page = new PageImpl<>(content, pageable, 3);

            // Act
            PageInfo result = pageMapper.toPageInfo(page);

            // Assert
            assertAll("큰 페이지 크기 검증",
                    () -> assertThat(result.getCurrentPage()).isEqualTo(0), // 0-based 유지
                    () -> assertThat(result.getPageSize()).isEqualTo(1000),
                    () -> assertThat(result.getTotalElements()).isEqualTo(3),
                    () -> assertThat(result.getTotalPages()).isEqualTo(1),
                    () -> assertThat(result.getNumberOfElements()).isEqualTo(3),
                    () -> assertThat(result.isFirst()).isTrue(),
                    () -> assertThat(result.isLast()).isTrue()
            );
        }
    }

    @Nested
    @DisplayName("데이터 일관성 테스트")
    class DataConsistencyTest {

        @Test
        @DisplayName("매핑된 모든 필드가 원본 Page 객체와 일관성을 유지한다")
        void testToPageInfo_allFields_shouldMaintainConsistency() {
            // Arrange
            List<String> content = Arrays.asList("a", "b", "c", "d");
            Pageable pageable = PageRequest.of(1, 3);
            Page<String> page = new PageImpl<>(content, pageable, 10);

            // Act
            PageInfo result = pageMapper.toPageInfo(page);

            // Assert - 원본과의 일관성 검증
            assertAll("데이터 일관성 검증",
                    // 직접 매핑된 값들 검증
                    () -> assertThat(result.getCurrentPage()).isEqualTo(page.getNumber()), // 0-based 유지
                    () -> assertThat(result.getPageSize()).isEqualTo(page.getSize()),
                    () -> assertThat(result.getTotalElements()).isEqualTo(page.getTotalElements()),
                    () -> assertThat(result.getTotalPages()).isEqualTo(page.getTotalPages()),
                    () -> assertThat(result.getNumberOfElements()).isEqualTo(page.getNumberOfElements()),
                    () -> assertThat(result.isFirst()).isEqualTo(page.isFirst()),
                    () -> assertThat(result.isLast()).isEqualTo(page.isLast()),
                    () -> assertThat(result.isEmpty()).isEqualTo(page.isEmpty()),

                    // expression으로 매핑된 값들 검증
                    () -> assertThat(result.isHasNext()).isEqualTo(page.hasNext()),
                    () -> assertThat(result.isHasPrevious()).isEqualTo(page.hasPrevious())
            );
        }

        @Test
        @DisplayName("매핑 결과가 논리적으로 일관성을 유지한다")
        void testToPageInfo_logicalConsistency_shouldBeValid() {
            // Arrange
            List<String> content = Arrays.asList("item1", "item2");
            Pageable pageable = PageRequest.of(1, 5);
            Page<String> page = new PageImpl<>(content, pageable, 12);

            // Act
            PageInfo result = pageMapper.toPageInfo(page);

            // Assert - 논리적 일관성 검증
            assertAll("논리적 일관성 검증",
                    // 페이지 번호 관련 (0-based)
                    () -> assertThat(result.getCurrentPage()).isGreaterThanOrEqualTo(0),
                    () -> assertThat(result.getCurrentPage()).isLessThan(result.getTotalPages()),

                    // 요소 수 관련
                    () -> assertThat(result.getNumberOfElements()).isLessThanOrEqualTo(result.getPageSize()),
                    () -> assertThat(result.getNumberOfElements()).isLessThanOrEqualTo((int) result.getTotalElements()),

                    // 페이지 상태 관련
                    () -> {
                        if (result.isFirst()) {
                            assertThat(result.isHasPrevious()).isFalse();
                        }
                    },
                    () -> {
                        if (result.isLast()) {
                            assertThat(result.isHasNext()).isFalse();
                        }
                    },
                    () -> {
                        if (result.isEmpty()) {
                            assertThat(result.getNumberOfElements()).isEqualTo(0);
                        }
                    }
            );
        }
    }
}