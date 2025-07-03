/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mz.sisden.sisden.configuration.AppConstants;
import mz.sisden.sisden.configuration.security.Securities;
import mz.sisden.sisden.repositories.*;
import mz.sisden.sisden.services.user.UserSaver;
import mz.sisden.sisden.services.user.UserGroupService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import mz.sisden.sisden.services.permission.InstitutionUserUserGroupPermissionService;
import mz.sisden.sisden.services.permission.PermissionService;
import mz.sisden.sisden.services.module.ModuleService;

@Getter
@Component
@RequiredArgsConstructor
public class Beans {
    private final AppConstants appConstants;
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final Notificator notificator;
    private final Securities securities;

    private final NotificationRepository notificationRepository;

    //repositories-services

    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
    private final UserSaver userSaver;
    private final UserGroupService userGroupService;

    private final ReportTypeRepository reportTypeRepository;
    private final ReportClassificationRepository reportClassificationRepository;
    private final InstituitionRepository instituitionRepository;
    private final TeamRepository teamRepository;
    private final ReportRepository reportRepository;
    private final PermissionRepository permissionRepository;
    private final ModuleRepository moduleRepository;
    
    // Permission services
    private final InstitutionUserUserGroupPermissionService institutionUserUserGroupPermissionService;
    private final PermissionService permissionService;
    private final ModuleService moduleService;
}
