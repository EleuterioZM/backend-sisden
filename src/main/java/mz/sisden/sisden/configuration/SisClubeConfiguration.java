/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.configuration;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.zkoss.spring.config.ZkScopesConfigurer;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
@SuppressWarnings("deprecation")
public class SisClubeConfiguration {
    private final DataSource dataSource;
    private final Environment environment;

    @Bean
    public XmlMapper xmlMapper() {
        return new XmlMapper();
    }

    @Bean
    @Primary
    public JsonMapper jsonMapper() {
        return new JsonMapper();
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(this.dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
        return new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        Map<String, PasswordEncoder> passwordEncoderMap = new HashMap<>();

        passwordEncoderMap.put("bcrypt", new BCryptPasswordEncoder());
        passwordEncoderMap.put("noop", NoOpPasswordEncoder.getInstance());

        return new DelegatingPasswordEncoder("bcrypt", passwordEncoderMap);
    }

    @Bean
    public static BeanFactoryPostProcessor beanFactoryPostProcessor() {
        return new ZkScopesConfigurer();
    }

    @Bean
    @Primary
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .connectTimeout(Duration.ofMinutes(5))
                .readTimeout(Duration.ofMinutes(5))
                .build();
    }

    @Bean(name = "sisClubePath")
    public Path getPath() {
        return Paths.get(this.environment.getRequiredProperty("sisden.path"));
    }

    @PostConstruct
    public void postConstruct() throws IOException {
        log.debug("{} - postConstruct", this.getClass().getName());

        log.debug("Checking existence of {} directory!", this.getPath());
        if (Files.notExists(this.getPath())) {
            log.debug("Directory {} not exists, creating new one.", this.getPath());
            Files.createDirectories(this.getPath());
            log.debug("Directory created successfully.");
        }

        if (!Files.isReadable(this.getPath()) || !Files.isWritable(this.getPath())) {
            log.warn("Directory {} not readable or writable!", this.getPath());
        }
    }
}
