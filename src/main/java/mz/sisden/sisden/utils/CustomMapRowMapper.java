/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.utils;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CustomMapRowMapper implements RowMapper<CustomMap> {
    @Override
    public CustomMap mapRow(ResultSet rs, int rowNum) throws SQLException {
        CustomMap rowMap = new CustomMap();
        int columnCount = rs.getMetaData().getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            String columnName = rs.getMetaData().getColumnName(i);
            int columnType = rs.getMetaData().getColumnType(i);

            Object value = switch (columnType) {
                case Types.DATE -> rs.getObject(i, LocalDate.class);
                case Types.TIME -> rs.getObject(i, LocalTime.class);
                case Types.TIMESTAMP -> rs.getObject(i, LocalDateTime.class);
                default -> rs.getObject(i);
            };

            rowMap.put(columnName, value);
        }

        return rowMap;
    }
}
