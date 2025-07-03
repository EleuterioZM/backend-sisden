/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.configuration;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.entities.SystemFile;
import mz.sisden.sisden.utils.DateTime;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.zkoss.bind.proxy.ProxyHelper;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@Getter
@Component
public class AppContext implements ApplicationContextAware {
    @Getter
    private static ApplicationContext context;

    public static <T> Optional<T> findByClass(Class<T> tClass) {
        T bean = null;
        try {
            bean = context.getBean(tClass);
        } catch (Exception ignore) {
            log.error("Failed to get bean of class {}.", tClass.getName());
        }
        return Optional.ofNullable(bean);
    }

    public static <T> T getByClass(Class<T> tClass) {
        return context.getBean(tClass);
    }

    public static void setContext(ApplicationContext applicationContext) {
        AppContext.context = applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;

        Locale locale = new Locale("pt", "MZ");
        DateTime.setLocale(locale);

        ProxyHelper.addIgnoredProxyClass(SystemFile.class);
        ProxyHelper.addIgnoredSuperProxyClass(List.class);
    }
}
