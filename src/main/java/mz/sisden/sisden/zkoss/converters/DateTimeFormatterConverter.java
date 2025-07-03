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

import java.time.LocalDateTime;
import java.util.Objects;

@org.springframework.stereotype.Component
@RequiredArgsConstructor
public class DateTimeFormatterConverter implements Converter<String, LocalDateTime, Component> {

    @Override
    public String coerceToUi(LocalDateTime dateTime, Component component, BindContext ctx) {
        if (Objects.isNull(dateTime))
            return "";

        return DateTime.formatDateTime(dateTime);
    }

    @Override
    public LocalDateTime coerceToBean(String compAttr, Component component, BindContext ctx) {
        return null;
    }
}
