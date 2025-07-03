/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.zkoss.validators;

import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.configuration.ZkComponent;
import mz.sisden.sisden.zkoss.ZkValidator;
import org.apache.commons.lang3.StringUtils;
import org.zkoss.bind.Property;
import org.zkoss.bind.ValidationContext;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;

import static java.util.Objects.isNull;

@Slf4j
@ZkComponent
public class NotNullValidator extends ZkValidator {
    @Override
    public void validate(ValidationContext ctx) {
        Component component = ctx.getBindContext().getComponent();
        Property property = ctx.getProperty();
        Object value = property.getValue();

        log.debug("Validate component {} with value {}", component, value);

        if (isNull(value) || (value instanceof String string && StringUtils.isBlank((string)))) {
            throw new WrongValueException(component, "Campo é obrigatório!");
        }
    }
}
