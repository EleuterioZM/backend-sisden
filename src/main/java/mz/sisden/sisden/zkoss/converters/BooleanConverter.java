

/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.zkoss.converters;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;

import java.util.Objects;

@org.springframework.stereotype.Component
@RequiredArgsConstructor
public class BooleanConverter implements Converter<String, Boolean, Component> {

    @Override
    public String coerceToUi(Boolean value, Component component, BindContext ctx) {
        if (Objects.isNull(value))
            value = Boolean.FALSE;

        return value ? "Sim" : "Não";
    }

    @Override
    public Boolean coerceToBean(String compAttr, Component component, BindContext ctx) {
        return StringUtils.trimToEmpty(compAttr).equals("Sim");
    }
}
