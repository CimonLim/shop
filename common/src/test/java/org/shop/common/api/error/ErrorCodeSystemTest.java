package org.shop.common.api.error;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 전역 ErrorCode 정책 테스트
 * - 범위 일관성
 * - 중복 방지
 * - 카테고리별 블록 유지
 */
class ErrorCodeSystemTest {


    private static final Map<Class<? extends ErrorCodeIfs>, IntRange> EXPECTED_RANGES = Map.of(
            UserErrorCode.class,            range(1000, 1999),
            BusinessLogicErrorCode.class,   range(2000, 2199),
            InputValidationErrorCode.class, range(2200, 2399),
            PermissionErrorCode.class,      range(2400, 2499),
            ResourceErrorCode.class,        range(2500, 2699),
            FileErrorCode.class,            range(2700, 2899),
            TokenErrorCode.class,           range(2900, 2999),
            DatabaseErrorCode.class,        range(3000, 3499),
            ServerErrorCode.class,          range(5000, 5999)
    );

    @Test
    @DisplayName("모든 에러코드 숫자 값은 전역에서 유일해야 한다")
    void errorCodesShouldBeUnique() {
        List<ErrorCodeIfs> allCodes = getAllErrorCodes();

        Map<Integer, List<ErrorCodeIfs>> grouped = allCodes.stream()
                .collect(Collectors.groupingBy(ErrorCodeIfs::getErrorCodeValue));

        Map<Integer, List<ErrorCodeIfs>> duplicates = grouped.entrySet().stream()
                .filter(e -> e.getValue().size() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        assertTrue(duplicates.isEmpty(),
                () -> "중복된 코드 발견: " + formatDuplicates(duplicates));
    }

    @Test
    @DisplayName("각 Enum 은 자신의 할당 범위 내 값만 사용해야 한다")
    void eachEnumShouldStayInItsAssignedRange() {
        EXPECTED_RANGES.forEach((enumClass, range) -> {
            ErrorCodeIfs[] constants = enumClass.getEnumConstants();
            assertNotNull(constants, "Enum 상수를 불러올 수 없습니다: " + enumClass.getSimpleName());

            List<ErrorCodeIfs> outOfRange = Arrays.stream(constants)
                    .filter(c -> !range.contains(c.getErrorCodeValue()))
                    .toList();

            assertTrue(outOfRange.isEmpty(),
                    () -> enumClass.getSimpleName() + " 범위 위반: " +
                            outOfRange.stream()
                                    .map(this::codeDesc)
                                    .collect(Collectors.joining(", ")) +
                            " (허용범위: " + range.start + "~" + range.end + ")");
        });
    }

    @Test
    @DisplayName(" 범위끼리 서로 겹치지 않아야 한다")
    void rangesShouldNotOverlap() {
        List<Map.Entry<Class<? extends ErrorCodeIfs>, IntRange>> entries =
                new ArrayList<>(EXPECTED_RANGES.entrySet());

        for (int i = 0; i < entries.size(); i++) {
            for (int j = i + 1; j < entries.size(); j++) {
                IntRange r1 = entries.get(i).getValue();
                IntRange r2 = entries.get(j).getValue();
                boolean overlap = r1.overlaps(r2);
                int finalI = i;
                int finalJ = j;
                assertFalse(overlap,
                        () -> "범위 겹침: "
                                + entries.get(finalI).getKey().getSimpleName() + "(" + r1.start + "~" + r1.end + ") vs "
                                + entries.get(finalJ).getKey().getSimpleName() + "(" + r2.start + "~" + r2.end + ")");
            }
        }
    }

    // ----------------- Helper -----------------

    private List<ErrorCodeIfs> getAllErrorCodes() {
        return Stream.of(
                        BusinessLogicErrorCode.values(),
                        DatabaseErrorCode.values(),
                        FileErrorCode.values(),
                        InputValidationErrorCode.values(),
                        PermissionErrorCode.values(),
                        ResourceErrorCode.values(),
                        ServerErrorCode.values(),
                        TokenErrorCode.values(),
                        UserErrorCode.values()
                ).flatMap(Arrays::stream)
                .map(e -> (ErrorCodeIfs) e) // 명시적 업캐스트
                .collect(Collectors.toList());
    }

    private String codeDesc(ErrorCodeIfs c) {
        return c.getClass().getSimpleName() + "." + ((Enum<?>) c).name()
                + "=" + c.getErrorCodeValue();
    }

    private String formatDuplicates(Map<Integer, List<ErrorCodeIfs>> duplicates) {
        return duplicates.entrySet().stream()
                .map(e -> e.getKey() + ": " + e.getValue().stream()
                        .map(this::codeDesc)
                        .collect(Collectors.joining(", ")))
                .collect(Collectors.joining("; "));
    }

    // ----------------- Range Utility -----------------

    private static IntRange range(int start, int end) {
        return new IntRange(start, end);
    }

    /**
         * 간단한 닫힌 구간 [start, end]
         */
        private record IntRange(int start, int end) {
        private IntRange {
            if (start > end) throw new IllegalArgumentException("start > end: " + start + " / " + end);
        }

            boolean contains(int v) {
                return v >= start && v <= end;
            }

            boolean overlaps(IntRange other) {
                return this.start <= other.end && other.start <= this.end;
            }
        }
}
