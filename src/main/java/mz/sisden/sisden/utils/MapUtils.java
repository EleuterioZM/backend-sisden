/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.utils;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.trimToNull;

public class MapUtils {


    public static LocalDate getLocalDate(Map<String, Object> data, String key) {
        Object object = data.get(key);

        if (nonNull(object) && object instanceof Timestamp timestamp) {
            LocalDateTime localDateTime = timestamp.toLocalDateTime();
            return localDateTime.toLocalDate();
        } else if (nonNull(object) && object instanceof Date date) {
            return date.toLocalDate();
        } else if (nonNull(object) && object instanceof LocalDate date) {
            return date;
        } else if (nonNull(object) && object instanceof LocalDateTime localDateTime) {
            return localDateTime.toLocalDate();
        }

        return null;
    }

    public static LocalDateTime getLocalDateTime(Map<String, Object> data, String key) {
        Object object = data.get(key);

        if (nonNull(object) && object instanceof Timestamp timestamp) {
            return timestamp.toLocalDateTime();
        } else if (nonNull(object) && object instanceof Date date) {
            Instant instant = date.toInstant();
            return LocalDateTime.ofInstant(instant, DateTime.zoneOffset());
        } else if (nonNull(object) && object instanceof LocalDateTime dateTime) {
            return dateTime;
        }

        return null;
    }

    public static String getString(Map<String, Object> map, String key) {
        return trimToNull(Objects.toString(map.get(key), ""));
    }

    public static BigDecimal getBigDecimal(Map<String, Object> map, String key) {
        String trimmedToNull = trimToNull(Objects.toString(map.get(key), ""));
        if (isNull(trimmedToNull)) {
            return null;
        }
        return BigDecimal.valueOf(Double.parseDouble(trimmedToNull));
    }

    public static Double getDouble(Map<String, Object> map, String key) {
        String trimmedToNull = trimToNull(Objects.toString(map.get(key), ""));
        if (isNull(trimmedToNull)) {
            return null;
        }
        return Double.parseDouble(trimmedToNull);
    }

    public static Long getLong(Map<String, Object> map, String key) {
        String trimmedToNull = trimToNull(Objects.toString(map.get(key), ""));
        if (isNull(trimmedToNull)) {
            return null;
        }
        return Long.parseLong(trimmedToNull);
    }

    public static byte[] getByteArray(Map<String, Object> map, String key) {
        Object byteAsObject = map.get(key);
        if (nonNull(byteAsObject)) {
            return (byte[]) byteAsObject;
        }
        return null;
    }

    public static Integer getInteger(Map<String, Object> map, String key) {
        String trimmedToNull = trimToNull(Objects.toString(map.get(key), ""));
        if (isNull(trimmedToNull)) {
            return null;
        }
        return Integer.parseInt(trimmedToNull);
    }

    public static Boolean getBoolean(Map<String, Object> map, String key) {
        return (Boolean) (map.get(key));
    }
}
