/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.utils;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.github.marlonlom.utilities.timeago.TimeAgoMessages;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.configuration.SisClubeException;
import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.capitalize;

@Slf4j
public class DateTime {

    private DateTime() {
    }

    @Getter
    private static final DateTimeFormatter iso8601Formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public static final DateTimeFormatter longDateFormatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", locale());

    public static String format(TemporalAccessor temporalAccessor, String pattern) {
        return DateTimeFormatter.ofPattern(pattern, locale()).format(temporalAccessor);
    }

    public static ZoneOffset zoneOffset() {
        return ZoneOffset.of("+2");
    }

    public static void setLocale(Locale locale) {
        Locale.setDefault(locale);
    }

    public static Locale locale() {
        return Locale.getDefault();
    }

    public static String monthName(LocalDate localDate) {
        return capitalize(localDate.getMonth().getDisplayName(TextStyle.FULL, locale()));
    }

    public static String monthYear(LocalDate localDate) {
        return capitalize(monthName(localDate)) + "/" + localDate.getYear();
    }

    public static String monthName(Month month) {
        return capitalize(month.getDisplayName(TextStyle.FULL, locale()));
    }

    public static String formatDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null)
            return "";
        return localDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'ás' HH:mm:ss"));
    }

    public static String formatDate(LocalDate localDate) {
        if (localDate == null)
            return "";
        return localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static Date fromLocalDateTimeToDate(LocalDateTime dateTime) {
        return DateTime.fromLocalDateTimeToDate(dateTime, null);
    }

    public static Date fromLocalDateTimeToDate(LocalDateTime dateTime, LocalDateTime _default) {
        try {
            return Date.from(dateTime.toInstant(DateTime.zoneOffset()));
        } catch (Exception exception) {
            log.warn("Error parsing (localDateTimeToDate) {}, default is {}. Exception Message: {}", dateTime, _default, exception.getMessage());
            if (isNull(_default)) {
                return null;
            } else {
                return DateTime.fromLocalDateTimeToDate(_default);
            }
        }
    }

    public static int currYear() {
        return YearMonth.now().getYear();
    }

    public static YearMonth currYearMonth() {
        return YearMonth.now();
    }

    public static LocalDate currDate() {
        return LocalDate.now();
    }

    public static LocalDateTime currDateTime() {
        return LocalDateTime.of(DateTime.currDate(), DateTime.currTime());
    }

    public static LocalTime currTime() {
        return LocalTime.now();
    }

    public static String timeAgo(LocalDateTime dateTime) {
        Locale LocaleBylanguageTag = Locale.forLanguageTag("pt");
        TimeAgoMessages messages = new TimeAgoMessages.Builder().withLocale(LocaleBylanguageTag).build();

        return TimeAgo.using(dateTime.toInstant(DateTime.zoneOffset()).toEpochMilli(), messages);
    }

    public static String duration(LocalDateTime start, LocalDateTime end) {
        Duration duration;
        if (nonNull(start) && nonNull(end)) {
            duration = Duration.between(start, end);
        } else if (nonNull(start)) {
            duration = Duration.between(start, LocalDateTime.now());
        } else {
            return null;
        }

        if (duration.toDays() > 0) {
            return Texter.format("{} dias, {}h{}m{}s", duration.toDays(), getHours(duration), getMinutes(duration), getSeconds(duration));
        }

        if (duration.toHours() > 0) {
            return Texter.format("{}h{}m{}s", getHours(duration), getMinutes(duration), getSeconds(duration));
        }

        if (duration.toMinutes() > 0) {
            return Texter.format("{}m{}s", getMinutes(duration), getSeconds(duration));
        }

        return Texter.format("{}s", getSeconds(duration));
    }

    public static String getHours(Duration duration) {
        return StringUtils.leftPad(Objects.toString(duration.toHours() - (duration.toDays() * 24)), 2, '0');
    }

    public static String getMinutes(Duration duration) {
        return StringUtils.leftPad(Objects.toString(duration.toMinutes() - (duration.toHours() * 60)), 2, '0');
    }

    public static String getSeconds(Duration duration) {
        return StringUtils.leftPad(Objects.toString(duration.getSeconds() - (duration.toMinutes() * 60)), 2, '0');
    }

    public static String monthYearInterval(List<LocalDate> localDateList) {
        return Texter.monthYearInterval(localDateList);
    }

    public static int calcAge(LocalDate date) {
        if (nonNull(date)) {
            return (int) ChronoUnit.YEARS.between(date, currDate());
        } else {
            return 0;
        }
    }

    public static boolean isInYearMonthRange(TemporalAccessor value, TemporalAccessor start, TemporalAccessor end) {

        if (isNull(value) || isNull(start)) {
            throw new SisClubeException("Value '{}' or start '{}' are null, they cannot be null, we need to know the point in time and when started the range", value, start);
        }

        YearMonth valueYearMonth = YearMonth.from(value);
        YearMonth startYearMonth = YearMonth.from(start);

        return startYearMonth.equals(valueYearMonth) ||
                (startYearMonth.isBefore(valueYearMonth) &&
                        (isNull(end) ||
                                YearMonth.from(end).isAfter(valueYearMonth) ||
                                YearMonth.from(end).equals(valueYearMonth)
                        )
                );
    }

    public static LocalDate determineBillDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }

        if (localDate.getDayOfMonth() >= 25) {
            return localDate.withDayOfMonth(1);
        } else {
            return localDate.plusMonths(1).withDayOfMonth(1);
        }
    }

    public static LocalDate determineBillDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }

        if (localDateTime.getDayOfMonth() >= 25) {
            return localDateTime.withDayOfMonth(1).toLocalDate();
        } else {
            return localDateTime.plusMonths(1).withDayOfMonth(1).toLocalDate();
        }
    }

    public static boolean billYearMonth(LocalDate localDate) {
        if (localDate == null) {
            return false;
        }

        return localDate.getDayOfMonth() >= 25;
    }

    public static boolean billYearMonth(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return false;
        }

        return localDateTime.getDayOfMonth() >= 25;
    }

    public static List<LocalDate> genDateAround(LocalDate localDate, int quantity) {
        List<LocalDate> localDateList = new ArrayList<>();

        LocalDate localDateAfter = localDate;
        for (int i = 0; i < quantity; i++) {
            localDateAfter = localDateAfter.plusMonths(1);
            localDateList.add(localDateAfter);
        }

        LocalDate localDateBefore = localDate;
        for (int i = 0; i < quantity; i++) {
            localDateBefore = localDateBefore.minusMonths(1);
            localDateList.add(localDateBefore);
        }
        return localDateList;
    }

    public static void fillMap(Map<String, Object> paramMap) {
        paramMap.put("curr_second", DateTime.currTime().getSecond());
        paramMap.put("curr_minute", DateTime.currTime().getMinute());
        paramMap.put("curr_hour", DateTime.currTime().getHour());
        paramMap.put("curr_day_of_year", DateTime.currDate().getDayOfYear());
        paramMap.put("curr_day_of_month", DateTime.currDate().getDayOfMonth());
        paramMap.put("curr_month", DateTime.currYearMonth().getMonthValue());
        paramMap.put("curr_year", DateTime.currYear());
    }
}
