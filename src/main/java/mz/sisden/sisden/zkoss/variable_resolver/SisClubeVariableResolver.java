/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.zkoss.variable_resolver;


import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.configuration.AppConstants;
import mz.sisden.sisden.configuration.AppContext;
import org.apache.commons.lang3.StringUtils;
import org.zkoss.xel.VariableResolver;
import org.zkoss.xel.XelException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class SisClubeVariableResolver implements VariableResolver {
    @Override
    public Object resolveVariable(String name) throws XelException {
        return this.getAppConstant(name);
    }

    public Object getAppConstant(String name) {
        Method[] methods = AppConstants.class.getMethods();
        for (Method method : methods) {
            String methodName = method.getName();

            String methodNameWithoutGet = StringUtils.remove(methodName, "get");
            methodNameWithoutGet = StringUtils.uncapitalize(methodNameWithoutGet);

            if (methodNameWithoutGet.equalsIgnoreCase(name)) {
                try {
                    return method.invoke(AppContext.getByClass(AppConstants.class));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    log.warn("Failed to get variable {} due to: ", name, e);
                }
            }
        }
        return null;
    }
}
