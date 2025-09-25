package org.shop.common.utils;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("HTMLCharacterEscapes 테스트")
class HTMLCharacterEscapesTest {

    private HTMLCharacterEscapes htmlCharacterEscapes;

    @BeforeEach
    void setUp() {
        // Arrange: 테스트 대상 객체 초기화
        htmlCharacterEscapes = new HTMLCharacterEscapes();
    }

    @Nested
    @DisplayName("생성자 테스트")
    class ConstructorTest {

        @Test
        @DisplayName("HTMLCharacterEscapes 인스턴스가 정상적으로 생성된다")
        void testConstructor_shouldCreateInstance() {
            // Act
            HTMLCharacterEscapes instance = new HTMLCharacterEscapes();

            // Assert
            assertThat(instance).isNotNull();
            assertThat(instance.getEscapeCodesForAscii()).isNotNull();
        }

        @Test
        @DisplayName("생성자에서 ASCII 이스케이프 배열이 올바르게 초기화된다")
        void testConstructor_shouldInitializeAsciiEscapesCorrectly() {
            // Act
            int[] escapes = htmlCharacterEscapes.getEscapeCodesForAscii();

            // Assert
            assertThat(escapes).isNotNull();
            assertThat(escapes).hasSize(128); // ASCII 문자 범위

            // XSS 방지 문자들이 ESCAPE_CUSTOM으로 설정되었는지 확인
            assertThat(escapes['<']).isEqualTo(CharacterEscapes.ESCAPE_CUSTOM);
            assertThat(escapes['>']).isEqualTo(CharacterEscapes.ESCAPE_CUSTOM);
            assertThat(escapes['&']).isEqualTo(CharacterEscapes.ESCAPE_CUSTOM);
            assertThat(escapes['"']).isEqualTo(CharacterEscapes.ESCAPE_CUSTOM);
            assertThat(escapes['\'']).isEqualTo(CharacterEscapes.ESCAPE_CUSTOM);
        }
    }

    @Nested
    @DisplayName("getEscapeCodesForAscii 메서드 테스트")
    class GetEscapeCodesForAsciiTest {

        @Test
        @DisplayName("ASCII 이스케이프 코드 배열을 반환한다")
        void testGetEscapeCodesForAscii_shouldReturnAsciiEscapeArray() {
            // Act
            int[] result = htmlCharacterEscapes.getEscapeCodesForAscii();

            // Assert
            assertThat(result).isNotNull();
            assertThat(result).hasSize(128);
        }


        @Test
        @DisplayName("XSS 방지 문자들이 ESCAPE_CUSTOM으로 설정된다")
        void testGetEscapeCodesForAscii_shouldSetXssPreventionCharactersAsCustomEscape() {
            // Act
            int[] result = htmlCharacterEscapes.getEscapeCodesForAscii();

            // Assert
            assertThat(result['<']).isEqualTo(CharacterEscapes.ESCAPE_CUSTOM);
            assertThat(result['>']).isEqualTo(CharacterEscapes.ESCAPE_CUSTOM);
            assertThat(result['&']).isEqualTo(CharacterEscapes.ESCAPE_CUSTOM);
            assertThat(result['"']).isEqualTo(CharacterEscapes.ESCAPE_CUSTOM);
            assertThat(result['\'']).isEqualTo(CharacterEscapes.ESCAPE_CUSTOM);
        }

        @Test
        @DisplayName("일반 문자들은 이스케이프되지 않는다")
        void testGetEscapeCodesForAscii_shouldNotEscapeNormalCharacters() {
            // Act
            int[] result = htmlCharacterEscapes.getEscapeCodesForAscii();

            // Assert - 일반 문자들은 ESCAPE_NONE (0) 이어야 함
            assertThat(result['a']).isEqualTo(CharacterEscapes.ESCAPE_NONE);
            assertThat(result['A']).isEqualTo(CharacterEscapes.ESCAPE_NONE);
            assertThat(result['1']).isEqualTo(CharacterEscapes.ESCAPE_NONE);
            assertThat(result['!']).isEqualTo(CharacterEscapes.ESCAPE_NONE);
            assertThat(result['@']).isEqualTo(CharacterEscapes.ESCAPE_NONE);
            assertThat(result['#']).isEqualTo(CharacterEscapes.ESCAPE_NONE);
        }

        @Test
        @DisplayName("동일한 배열 인스턴스를 반환한다")
        void testGetEscapeCodesForAscii_shouldReturnSameArrayInstance() {
            // Act
            int[] result1 = htmlCharacterEscapes.getEscapeCodesForAscii();
            int[] result2 = htmlCharacterEscapes.getEscapeCodesForAscii();

            // Assert
            assertThat(result1).isSameAs(result2);
        }
    }

    @Nested
    @DisplayName("getEscapeSequence 메서드 테스트")
    class GetEscapeSequenceTest {

        @Nested
        @DisplayName("XSS 방지 문자 이스케이프 테스트")
        class XssPreventionCharacterTest {


            @ParameterizedTest
            @DisplayName("XSS 방지 문자들을 HTML 엔티티로 이스케이프한다")
            @CsvSource({
                    "'<', '&lt;', '< 문자를 &lt;로 이스케이프한다'",
                    "'>', '&gt;', '> 문자를 &gt;로 이스케이프한다'",
                    "'&', '&amp;', '& 문자를 &amp;로 이스케이프한다'",
                    "'\"', '&quot;', '\" 문자를 &quot;로 이스케이프한다'",
                    "'''', '&#x27;', ''' 문자를 &#x27;로 이스케이프한다'"
            })
            void testGetEscapeSequence_withXssCharacters_shouldReturnHtmlEntities(
                    char inputChar,
                    String expectedEscape,
                    String description) {
                // Act
                SerializableString result = htmlCharacterEscapes.getEscapeSequence(inputChar);

                // Assert
                assertThat(result)
                        .as(description)
                        .isNotNull()
                        .isInstanceOf(SerializedString.class);
                assertThat(result.getValue())
                        .as(description)
                        .isEqualTo(expectedEscape);
            }

            @ParameterizedTest
            @DisplayName("모든 XSS 방지 문자들이 올바른 HTML 엔티티로 변환된다")
            @CsvSource({
                    "'<', '&lt;'",
                    "'>', '&gt;'",
                    "'&', '&amp;'",
                    "'\"', '&quot;'",
                    "'''', '&#x27;'"
            })
            void testGetEscapeSequence_withXssCharacters_shouldReturnCorrectEntities(char input, String expected) {
                // Act
                SerializableString result = htmlCharacterEscapes.getEscapeSequence(input);

                // Assert
                assertThat(result).isNotNull();
                assertThat(result.getValue()).isEqualTo(expected);
            }
        }

        @Nested
        @DisplayName("일반 문자 처리 테스트")
        class NormalCharacterTest {

            @ParameterizedTest
            @DisplayName("일반 문자들은 null을 반환한다")
            @ValueSource(chars = {'a', 'A', '1', '!', '@', '#', '$', '%', '^', '*', '(', ')', '-', '_', '=', '+', '[', ']', '{', '}', '|', '\\', ':', ';', '?', '/', '.', ',', '~', '`'})
            void testGetEscapeSequence_withNormalCharacters_shouldReturnNull(char input) {
                // Act
                SerializableString result = htmlCharacterEscapes.getEscapeSequence(input);

                // Assert
                assertThat(result).isNull();
            }

            @Test
            @DisplayName("공백 문자는 null을 반환한다")
            void testGetEscapeSequence_withSpace_shouldReturnNull() {
                // Act
                SerializableString result = htmlCharacterEscapes.getEscapeSequence(' ');

                // Assert
                assertThat(result).isNull();
            }

            @Test
            @DisplayName("숫자 문자들은 null을 반환한다")
            void testGetEscapeSequence_withDigits_shouldReturnNull() {
                // Arrange & Act & Assert
                for (char digit = '0'; digit <= '9'; digit++) {
                    SerializableString result = htmlCharacterEscapes.getEscapeSequence(digit);
                    assertThat(result)
                            .as("숫자 '%c'는 이스케이프되지 않아야 함", digit)
                            .isNull();
                }
            }

            @Test
            @DisplayName("알파벳 문자들은 null을 반환한다")
            void testGetEscapeSequence_withAlphabets_shouldReturnNull() {
                // Arrange & Act & Assert
                for (char letter = 'a'; letter <= 'z'; letter++) {
                    SerializableString result = htmlCharacterEscapes.getEscapeSequence(letter);
                    assertThat(result)
                            .as("소문자 '%c'는 이스케이프되지 않아야 함", letter)
                            .isNull();
                }

                for (char letter = 'A'; letter <= 'Z'; letter++) {
                    SerializableString result = htmlCharacterEscapes.getEscapeSequence(letter);
                    assertThat(result)
                            .as("대문자 '%c'는 이스케이프되지 않아야 함", letter)
                            .isNull();
                }
            }
        }

        @Nested
        @DisplayName("경계 조건 테스트")
        class BoundaryConditionTest {

            @Test
            @DisplayName("ASCII 범위의 최소값(0)은 null을 반환한다")
            void testGetEscapeSequence_withMinAsciiValue_shouldReturnNull() {
                // Act
                SerializableString result = htmlCharacterEscapes.getEscapeSequence(0);

                // Assert
                assertThat(result).isNull();
            }

            @Test
            @DisplayName("ASCII 범위의 최대값(127)은 null을 반환한다")
            void testGetEscapeSequence_withMaxAsciiValue_shouldReturnNull() {
                // Act
                SerializableString result = htmlCharacterEscapes.getEscapeSequence(127);

                // Assert
                assertThat(result).isNull();
            }

            @Test
            @DisplayName("ASCII 범위를 벗어나는 유니코드 문자는 null을 반환한다")
            void testGetEscapeSequence_withUnicodeCharacter_shouldReturnNull() {
                // Act
                SerializableString result1 = htmlCharacterEscapes.getEscapeSequence(128); // ASCII 범위 초과
                SerializableString result2 = htmlCharacterEscapes.getEscapeSequence(256); // 확장 ASCII 범위
                SerializableString result3 = htmlCharacterEscapes.getEscapeSequence(0x1F600); // 이모지 😀

                // Assert
                assertThat(result1).isNull();
                assertThat(result2).isNull();
                assertThat(result3).isNull();
            }

            @Test
            @DisplayName("음수 문자 코드는 null을 반환한다")
            void testGetEscapeSequence_withNegativeCharCode_shouldReturnNull() {
                // Act
                SerializableString result = htmlCharacterEscapes.getEscapeSequence(-1);

                // Assert
                assertThat(result).isNull();
            }
        }
    }

    @Nested
    @DisplayName("통합 테스트")
    class IntegrationTest {

        @Test
        @DisplayName("이스케이프 코드와 이스케이프 시퀀스가 일관성 있게 동작한다")
        void testEscapeCodesAndSequences_shouldBeConsistent() {
            // Arrange
            char[] xssChars = {'<', '>', '&', '"', '\''};
            int[] escapes = htmlCharacterEscapes.getEscapeCodesForAscii();

            // Act & Assert
            for (char ch : xssChars) {
                // 이스케이프 코드가 ESCAPE_CUSTOM으로 설정되어 있어야 함
                assertThat(escapes[ch])
                        .as("문자 '%c'의 이스케이프 코드", ch)
                        .isEqualTo(CharacterEscapes.ESCAPE_CUSTOM);

                // 해당 문자의 이스케이프 시퀀스가 존재해야 함
                SerializableString sequence = htmlCharacterEscapes.getEscapeSequence(ch);
                assertThat(sequence)
                        .as("문자 '%c'의 이스케이프 시퀀스", ch)
                        .isNotNull();
                assertThat(sequence.getValue())
                        .as("문자 '%c'의 이스케이프 시퀀스 값", ch)
                        .isNotEmpty();
            }
        }

        @Test
        @DisplayName("XSS 공격 패턴이 안전하게 이스케이프된다")
        void testXssAttackPatterns_shouldBeEscapedSafely() {
            // Arrange - 일반적인 XSS 공격에 사용되는 문자들
            String xssPattern = "<script>alert('xss')</script>";

            // Act - 각 문자별로 이스케이프 확인
            StringBuilder escapedPattern = new StringBuilder();
            for (char ch : xssPattern.toCharArray()) {
                SerializableString escaped = htmlCharacterEscapes.getEscapeSequence(ch);
                if (escaped != null) {
                    escapedPattern.append(escaped.getValue());
                } else {
                    escapedPattern.append(ch);
                }
            }

            // Assert
            String result = escapedPattern.toString();
            assertThat(result).contains("&lt;script&gt;");
            assertThat(result).contains("alert(&#x27;xss&#x27;)");
            assertThat(result).contains("&lt;/script&gt;");
            assertThat(result).doesNotContain("<script>");
            assertThat(result).doesNotContain("</script>");
        }

        @Test
        @DisplayName("여러 인스턴스가 동일하게 동작한다")
        void testMultipleInstances_shouldBehaveSimilarly() {
            // Arrange
            HTMLCharacterEscapes instance1 = new HTMLCharacterEscapes();
            HTMLCharacterEscapes instance2 = new HTMLCharacterEscapes();
            char[] testChars = {'<', '>', '&', '"', '\'', 'a', '1'};

            // Act & Assert
            for (char ch : testChars) {
                SerializableString result1 = instance1.getEscapeSequence(ch);
                SerializableString result2 = instance2.getEscapeSequence(ch);

                if (result1 == null) {
                    assertThat(result2).isNull();
                } else {
                    assertThat(result2).isNotNull();
                    assertThat(result2.getValue()).isEqualTo(result1.getValue());
                }
            }
        }
    }

    @Nested
    @DisplayName("성능 테스트")
    class PerformanceTest {

        @Test
        @DisplayName("대량의 문자 처리 시 적절한 성능을 보인다")
        void testPerformance_withLargeNumberOfCharacters_shouldPerformWell() {
            // Arrange
            char[] testChars = new char[1000];
            for (int i = 0; i < 1000; i++) {
                testChars[i] = (char) (i % 128); // ASCII 범위 내 문자들
            }

            // Act
            long startTime = System.nanoTime();
            for (char ch : testChars) {
                htmlCharacterEscapes.getEscapeSequence(ch);
            }
            long endTime = System.nanoTime();

            // Assert - 1ms 이하로 처리되어야 함 (성능 기준)
            long durationMs = (endTime - startTime) / 1_000_000;
            assertThat(durationMs).isLessThan(1);
        }
    }

    @Nested
    @DisplayName("보안 테스트")
    class SecurityTest {

        @Test
        @DisplayName("모든 주요 XSS 벡터 문자가 이스케이프된다")
        void testXssVectorCharacters_shouldAllBeEscaped() {
            // Arrange - OWASP에서 권장하는 XSS 방지 대상 문자들
            char[] xssVectors = {'<', '>', '&', '"', '\''};

            // Act & Assert
            for (char vector : xssVectors) {
                SerializableString escaped = htmlCharacterEscapes.getEscapeSequence(vector);
                assertThat(escaped)
                        .as("XSS 벡터 문자 '%c'는 반드시 이스케이프되어야 함", vector)
                        .isNotNull();
                assertThat(escaped.getValue())
                        .as("XSS 벡터 문자 '%c'의 이스케이프 결과", vector)
                        .startsWith("&")
                        .endsWith(";");
            }
        }

        @Test
        @DisplayName("HTML 태그 구성 문자들이 모두 안전하게 처리된다")
        void testHtmlTagCharacters_shouldBeSafelyHandled() {
            // Arrange
            String htmlTag = "<div class=\"test\" id='myId'>";

            // Act & Assert
            for (char ch : htmlTag.toCharArray()) {
                if (ch == '<' || ch == '>' || ch == '"' || ch == '\'') {
                    SerializableString escaped = htmlCharacterEscapes.getEscapeSequence(ch);
                    assertThat(escaped)
                            .as("HTML 태그 문자 '%c'는 이스케이프되어야 함", ch)
                            .isNotNull();
                }
            }
        }
    }
}