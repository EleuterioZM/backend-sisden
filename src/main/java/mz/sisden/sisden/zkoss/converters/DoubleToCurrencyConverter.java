/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.zkoss.converters;

import lombok.RequiredArgsConstructor;
import mz.sisden.sisden.utils.Texter;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;

import java.util.Objects;

@org.springframework.stereotype.Component
@RequiredArgsConstructor
public class DoubleToCurrencyConverter implements Converter<String, Double, Component> {

    @Override
    public String coerceToUi(Double value, Component component, BindContext ctx) {
        if (Objects.isNull(value))
            return "";

        return Texter.toCurrencyString(value);
    }

    @Override
    public Double coerceToBean(String compAttr, Component component, BindContext ctx) {
        return null;
    }
}
