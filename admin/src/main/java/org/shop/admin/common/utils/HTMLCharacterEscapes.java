package org.shop.admin.common.utils;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;

/**
 * HTML 문자열의 XSS(Cross Site Scripting) 공격을 방지하기 위한 문자 이스케이프 처리 클래스
 * Jackson의 {@link CharacterEscapes}를 확장하여 HTML 특수 문자를 HTML 엔티티로 변환합니다.
 *
 * <p>이 클래스는 다음과 같은 문자들을 변환합니다:
 * <ul>
 *   <li>'<' → "&lt;"</li>
 *   <li>'>' → "&gt;"</li>
 *   <li>'&' → "&amp;"</li>
 *   <li>'"' → "&quot;"</li>
 *   <li>''' → "&#x27;"</li>
 * </ul>
 *
 *
 * @see CharacterEscapes
 * @see com.fasterxml.jackson.databind.ObjectMapper
 */
public class HTMLCharacterEscapes extends CharacterEscapes {

    /**
     * ASCII 문자에 대한 이스케이프 코드 배열
     */
    private final int[] asciiEscapes;

    /**
     * HTMLCharacterEscapes 인스턴스를 생성하고 이스케이프할 문자들을 초기화합니다.
     */
    public HTMLCharacterEscapes() {
        // JSON 표준 이스케이프 문자 설정을 기본값으로 사용
        asciiEscapes = CharacterEscapes.standardAsciiEscapesForJSON();

        // XSS 방지를 위한 추가 문자 이스케이프 설정
        asciiEscapes['<'] = ESCAPE_CUSTOM;
        asciiEscapes['>'] = ESCAPE_CUSTOM;
        asciiEscapes['&'] = ESCAPE_CUSTOM;
        asciiEscapes['"'] = ESCAPE_CUSTOM;
        asciiEscapes['\''] = ESCAPE_CUSTOM;
    }

    /**
     * ASCII 문자에 대한 이스케이프 코드 배열을 반환합니다.
     *
     * @return ASCII 문자의 이스케이프 코드가 저장된 정수 배열
     */
    @Override
    public int[] getEscapeCodesForAscii() {
        return asciiEscapes;
    }

    /**
     * 주어진 문자에 대한 이스케이프 시퀀스를 반환합니다.
     * {@link #ESCAPE_CUSTOM}으로 설정된 문자에 대해서만 호출됩니다.
     *
     * @param ch 이스케이프할 문자의 코드 포인트
     * @return 해당 문자의 HTML 엔티티 문자열, 이스케이프가 필요없는 경우 null
     */
    @Override
    public SerializableString getEscapeSequence(int ch) {
        return switch (ch) {
            case '<' -> new SerializedString("&lt;");
            case '>' -> new SerializedString("&gt;");
            case '&' -> new SerializedString("&amp;");
            case '"' -> new SerializedString("&quot;");
            case '\'' -> new SerializedString("&#x27;");
            default -> null;
        };
    }
}
