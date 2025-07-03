package mz.sisden.sisden.repositories;

import mz.sisden.sisden.entities.InstitutionUserUserGroupPermission;
import mz.sisden.sisden.entities.Instituition;
import mz.sisden.sisden.entities.User;
import mz.sisden.sisden.entities.UserGroup;
import mz.sisden.sisden.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstitutionUserUserGroupPermissionRepository extends JpaRepository<InstitutionUserUserGroupPermission, Long> {
    @Query("select iugp from InstitutionUserUserGroupPermission iugp where iugp.instituition = :instituition and iugp.user = :user and iugp.userGroup = :userGroup")
    List<InstitutionUserUserGroupPermission> findAllByInstituitionAndUserAndUserGroup(Instituition instituition, User user, UserGroup userGroup);

    @Query("select iugp from InstitutionUserUserGroupPermission iugp where iugp.instituition = :instituition and iugp.user = :user and iugp.permission = :permission")
    List<InstitutionUserUserGroupPermission> findAllByInstituitionAndUserAndPermission(Instituition instituition, User user, Permission permission);

    List<InstitutionUserUserGroupPermission> findAllByInstituition(Instituition instituition);
    List<InstitutionUserUserGroupPermission> findAllByUser(User user);
    List<InstitutionUserUserGroupPermission> findAllByUserGroup(UserGroup userGroup);
    List<InstitutionUserUserGroupPermission> findAllByPermission(Permission permission);
} 