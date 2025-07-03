/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.zkoss.converters;

import lombok.RequiredArgsConstructor;
import mz.sisden.sisden.utils.DateTime;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;

import java.time.LocalDate;
import java.util.Objects;

@org.springframework.stereotype.Component
@RequiredArgsConstructor
public class DateWithAgeConverter implements Converter<String, LocalDate, Component> {

    @Override
    public String coerceToUi(LocalDate date, Component component, BindContext ctx) {
        if (Objects.isNull(date))
            return "";

        return DateTime.formatDate(date) + " (" + DateTime.calcAge(date) + ")";
    }

    @Override
    public LocalDate coerceToBean(String compAttr, Component component, BindContext ctx) {
        return null;
    }
}
