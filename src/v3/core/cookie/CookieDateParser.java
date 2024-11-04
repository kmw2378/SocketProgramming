package v3.core.cookie;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class CookieDateParser {
    private static final DateTimeFormatter FLEXIBLE_FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()  // 대소문자 구분 없이 처리
            .appendPattern("EEE, ")  // 요일 (예: Wed,)
            .appendPattern("dd[-MMM][-MM]")  // 날짜, 월에 하이픈을 포함하거나 공백으로 처리
            .appendPattern("-yyyy ")  // 연도
            .appendPattern("HH:mm:ss ")  // 시, 분, 초
            .appendPattern("z")  // GMT 같은 시간대 표기
            .toFormatter(Locale.ENGLISH);

    public static ZonedDateTime parse(String dateString) {
        try {
            return ZonedDateTime.parse(dateString, FLEXIBLE_FORMATTER);
        } catch (DateTimeParseException e) {
            System.err.println("지원되지 않는 날짜 형식: " + dateString);
            throw e;
        }
    }
}