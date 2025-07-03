/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.configuration.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))

                .formLogin((form) -> form.loginPage("/login").permitAll())
                .formLogin((form) -> form.loginProcessingUrl("/login").permitAll())
                .formLogin((form) -> form.defaultSuccessUrl("/"))
                .formLogin((form) -> form.usernameParameter("usernameOrEmail"))
                .logout(LogoutConfigurer::permitAll)

                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                            authorizationManagerRequestMatcherRegistry
                                    .requestMatchers("/fe/**", "/api/authentication/**", "/api/report-type/**","/api/report-type/instituition/**","/api/reports","/api/reports/**","/api/report-classification", "/api/members", "/api/members/**", "/api/member/**", "/login.zul", "/assets/**", "/login", "/zkau/**", "/api/instituitions", "/api/instituitions/**")
                                    .permitAll()

                                    .requestMatchers("/**")
                                    .authenticated();
                        }
                );

        return httpSecurity.build();
    }
}
