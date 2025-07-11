/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.repositories;

import mz.sisden.sisden.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
