/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.configuration.security;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.entities.Permission;
import mz.sisden.sisden.entities.User;
import mz.sisden.sisden.entities.UserGroup;
import mz.sisden.sisden.entities.UserUserGroupPermission;
import mz.sisden.sisden.repositories.UserRepository;
import mz.sisden.sisden.repositories.UserUserGroupPermissionRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.zkoss.spring.security.SecurityUtil;

import java.util.List;

import static java.util.Objects.nonNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class Securities {
    private final UserUserGroupPermissionRepository userUserGroupPermissionRepository;
    private final UserRepository userRepository;

    public User getUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication instanceof UserAuthentication userAuthentication) {
            User user = userAuthentication.getUser();
            return this.userRepository.getReferenceById(user.getId());
        }
        return null;
    }

    public boolean isInUserGroup(UserGroup userGroup) {
        if (nonNull(this.getUser()) && nonNull(userGroup)) {
            List<UserUserGroupPermission> userUserGroupPermissionList = this.userUserGroupPermissionRepository.findAllByUserAndUserGroup(this.getUser(), userGroup);
            return CollectionUtils.isNotEmpty(userUserGroupPermissionList);
        }
        return false;
    }

    public boolean hasPermission(Permission permission) {
        if (nonNull(this.getUser()) && nonNull(permission)) {
            List<UserUserGroupPermission> userUserGroupPermissionList = this.userUserGroupPermissionRepository.findAllByUserAndPermission(this.getUser(), permission);
            return CollectionUtils.isNotEmpty(userUserGroupPermissionList);
        }
        return false;
    }

    //pode imprimir mais de uma vez o cartão de sócio?
    public boolean canPrint() {
        return SecurityUtil.isAllGranted("MEMBER_GENERATE_MULTIPLE_CARDS");
    }
}
