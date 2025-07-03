/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.zkoss.converters;

import lombok.RequiredArgsConstructor;
import mz.sisden.sisden.utils.Texter;
import org.apache.commons.collections4.CollectionUtils;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;

import java.util.List;

@org.springframework.stereotype.Component
@RequiredArgsConstructor
public class CommaSeparatedConverter implements Converter<String, List<Object>, Component> {
    @Override
    public String coerceToUi(List<Object> objectList, Component component, BindContext ctx) {
        if (CollectionUtils.isEmpty(objectList)) {
            return "";
        }

        return Texter.commaSeparated(objectList);
    }

    @Override
    public List<Object> coerceToBean(String compAttr, Component component, BindContext ctx) {
        return null;
    }
}
