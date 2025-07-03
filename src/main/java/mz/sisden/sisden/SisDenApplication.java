/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden;

import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.configuration.AppContext;
import mz.sisden.sisden.entities.SystemFile;
import mz.sisden.sisden.entities.User;
import mz.sisden.sisden.utils.DateTime;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.zkoss.bind.proxy.ProxyHelper;

import java.util.List;
import java.util.Locale;

@Slf4j
@EnableAsync
@EnableCaching
@EnableScheduling
@SpringBootApplication(exclude = {JdbcTemplateAutoConfiguration.class})
public class SisDenApplication {

    public static void main(String[] args) {
        Locale locale = new Locale("pt", "MZ");
        DateTime.setLocale(locale);

        ConfigurableApplicationContext applicationContext = SpringApplication.run(SisDenApplication.class, args);
        AppContext.setContext(applicationContext);

        ProxyHelper.addIgnoredProxyClass(SystemFile.class);
        ProxyHelper.addIgnoredSuperProxyClass(List.class);
    }
}
