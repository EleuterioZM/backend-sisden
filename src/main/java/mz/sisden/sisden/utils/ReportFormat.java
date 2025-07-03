/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Stream;


@Slf4j
@Getter
@AllArgsConstructor
public enum ReportFormat {
    PDF(
            "PDF",
            "pdf"
    ),
    XLSX(
            "Excel (XLSX)",
            "xlsx"
    ),
    XLS(
            "Excel (XLS)",
            "xls"
    ),
    ;

    final String name;
    final String extension;

    public static List<ReportFormat> getList() {
        return Stream.of(values()).toList();
    }
}
