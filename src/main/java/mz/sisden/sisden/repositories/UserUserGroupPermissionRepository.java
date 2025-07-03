/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.repositories;


import mz.sisden.sisden.entities.Permission;
import mz.sisden.sisden.entities.User;
import mz.sisden.sisden.entities.UserGroup;
import mz.sisden.sisden.entities.UserUserGroupPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserUserGroupPermissionRepository extends JpaRepository<UserUserGroupPermission, Long> {
    @Query("select uugp from UserUserGroupPermission uugp where uugp.user = :user and uugp.userGroup = :userGroup")
    List<UserUserGroupPermission> findAllByUserAndUserGroup(User user, UserGroup userGroup);

    @Query("select uugp from UserUserGroupPermission uugp where uugp.user = :user and uugp.permission = :permission")
    List<UserUserGroupPermission> findAllByUserAndPermission(User user, Permission permission);
}
