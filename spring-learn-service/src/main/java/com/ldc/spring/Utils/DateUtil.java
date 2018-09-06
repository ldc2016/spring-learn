package com.ldc.spring.Utils;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;

/**
 * created by liudacheng on 2018/9/5.
 */
public class DateUtil {

    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYYMMDD_HH_MM_SS = "yyyyMMdd HH:mm:ss";
    public static final String YYYYMMDD_HH_MM_SS_SSS = "yyyyMMdd HH:mm:ss.SSS";
    public static final String YYYY1MM1DD_HH_MM_SS_SSS = "yyyy/MM/dd HH:mm:ss.SSS";
    public static final String YYYY1MM1DD_HH_MM_SS = "yyyy/MM/dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(YYYYMMDDHHMMSS);

    private static final DateTimeFormatter DATE_TIME_FORMATTER_WITH_MILL = new DateTimeFormatterBuilder()
            .appendPattern(YYYYMMDDHHMMSS).appendValue(ChronoField.MILLI_OF_SECOND, 3).toFormatter();

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(YYYYMMDD);

    private static final DateTimeFormatter DATE_TIME_FORMATTER_STANDARD = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);

    private static final int DATE_LENGTH_OF_YYYYMMDD = 8;

    private static final int DATETIME_LENGTH_YYYYMMDDHHMMSS = 14;

    private static final LocalTime LOCAL_TIME = LocalTime.of(0, 0, 0);

    private DateUtil() {
    }

    public static Date localDateToDate(LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        return Date.from(zdt.toInstant());
    }

    public static LocalDateTime parseLocalDateTime(String text, String pattern) {
        return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime parseLocalDateTime(String text) {
        String normalText = normalizeTimeText(text);
        if (StringUtils.isBlank(normalText)) {
            return null;
        }
        if (normalText.length() == DATETIME_LENGTH_YYYYMMDDHHMMSS) {
            return LocalDateTime.parse(normalText, DATE_TIME_FORMATTER);
        }
        return LocalDateTime.parse(normalText, DATE_TIME_FORMATTER_WITH_MILL);
    }

    public static LocalDateTime parseLocalDateTime(long epochMilli) {
        Instant instant = Instant.ofEpochMilli(epochMilli);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static LocalDate parseLocalDate(String text) {
        String normalizeTimeText = normalizeTimeText(text);
        if (StringUtils.isBlank(normalizeTimeText)) {
            return null;
        }
        if (normalizeTimeText.length() < DATE_LENGTH_OF_YYYYMMDD) {
            return null;
        }
        return LocalDate.parse(normalizeTimeText(text).substring(0, 8), DATE_FORMATTER);
    }

    public static LocalDate parseLocalDate(String text, String pattern) {
        return LocalDate.parse(text, DateTimeFormatter.ofPattern(pattern));
    }

    public static String format(LocalDateTime localDateTime, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(localDateTime);
    }

    private static String normalizeTimeText(String text) {
        if (StringUtils.isNotBlank(text)) {
            StringBuilder result = new StringBuilder();
            char[] chars = text.toCharArray();
            for (char c : chars) {
                if (c >= 48 && c <= 57) {
                    result.append(c);
                }
            }
            return result.toString();
        }
        return text;
    }

    public static String dateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD);
        return dateFormat.format(date);
    }

    public static String formatDateToDateTime(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return LocalDateTime.of(localDate, LOCAL_TIME).format(DATE_TIME_FORMATTER_STANDARD);
    }
}
