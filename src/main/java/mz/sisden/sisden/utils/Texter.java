/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.collections4.ListUtils.emptyIfNull;

@Slf4j
@Component
public class Texter {
    private static final Locale ptMZlocale = new Locale("pt", "MZ");
    private static final NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(ptMZlocale);

    /**
     * return comma separated of value if string, or getName if has one
     *
     * @param tList
     * @param <T>
     * @return
     */
    public static <T> String commaSeparated(List<T> tList) {
        return commaSeparated(tList, t -> {
            if (t instanceof String s) {
                return s;
            }

            String value = Objects.toString(t, null);

            if (nonNull(value)) {
                Field[] allFields = FieldUtils.getAllFields(t.getClass());
                for (Field f : allFields) {
                    f.setAccessible(true);

                    // if has name field, just retrive  the value
                    if (f.getName().equals("name")) {
                        try {
                            Method method = MethodUtils.getMatchingMethod(t.getClass(), "getName");
                            value = (String) method.invoke(t);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    f.setAccessible(false);
                }
            }

            return value;
        });
    }

    public static <T> String commaSeparated(List<T> tList, Function<T, String> function) {
        StringBuilder text = new StringBuilder();

        List<String> strings = tList.stream()
                .map(function)
                .filter(StringUtils::isNotBlank)
                .toList();
        ListIterator<String> listIterator = emptyIfNull(strings).listIterator();
        while (listIterator.hasNext()) {
            String s = listIterator.next();

            if (StringUtils.isBlank(s)) {
                continue;
            }

            text.append(s);

            if (listIterator.hasNext()) {
                text.append(", ");
            }
        }
        return text.toString();
    }

    public static String monthYearInterval(List<LocalDate> localDateList) {
        StringBuilder stringBuilder = new StringBuilder();

        if (CollectionUtils.isEmpty(localDateList)) {
            return stringBuilder.toString();
        }

        localDateList = localDateList.stream().distinct().sorted(LocalDate::compareTo).collect(Collectors.toList());

        // return a month like "January/2024"
        if (localDateList.size() == 1) {
            LocalDate localDate = localDateList.get(0);
            stringBuilder.append(DateTime.monthYear(localDate));
            return stringBuilder.toString();
        }

        //return an interval like "October/2024 - April/2025"
        LocalDate firstLocalDate = localDateList.get(0);
        LocalDate lastLocalDate = localDateList.get(localDateList.size() - 1);

        stringBuilder.append(DateTime.monthYear(firstLocalDate));
        stringBuilder.append(" - ");
        stringBuilder.append(DateTime.monthYear(lastLocalDate));

        return stringBuilder.toString();
    }

    public static String format(String text, Object... objectArray) {
        return MessageFormatter.arrayFormat(text, objectArray).getMessage();
    }

    public static String inFull(Double value) {
        return CurrencyWriter.getInstance().write(BigDecimal.valueOf(value));
    }

    public static String toCurrencyString(Double decimal) {
        if (Objects.isNull(decimal))
            return "";
        return currencyInstance.format(decimal).replace(currencyInstance.getCurrency().getSymbol(), "").trim();
    }

    public static String normalizeFileName(String fileName) {
        return StringUtils.trimToEmpty(fileName).replaceAll("[\\\\:*?\"<>/|]", "_");
    }

    public static String monthsInDebtToDescription(Integer monthsInDebt) {
        if (isNull(monthsInDebt)) {
            return "Sem registos";
        }

        if (monthsInDebt <= 0) {
            return "Sem dívida!";
        } else if (monthsInDebt == 1) {
            return "Dívida 1 mês";
        } else if (monthsInDebt <= 3) {
            return "Dívida 2-3 meses";
        } else if (monthsInDebt <= 6) {
            return "Dívida 4-6 meses";
        } else if (monthsInDebt <= 12) {
            return "Dívida 7-12 meses";
        } else {
            return "Dívida +13 meses";
        }
    }

    public static String encodeBase64(byte[] bytes) {
        if (isNull(bytes)) {
            return null;
        }
        return Base64.getEncoder().encodeToString(bytes);
    }
}
