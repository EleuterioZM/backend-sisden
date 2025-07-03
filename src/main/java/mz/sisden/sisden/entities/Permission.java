/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import mz.sisden.sisden.configuration.BaseEntity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import java.util.ArrayList;
import java.util.List;

@Table
@Getter
@Setter
@Entity
@Audited
@AuditOverride
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Permission extends BaseEntity {
    @Id
    @SequenceGenerator(name = "permission_id_sequence", allocationSize = 1)
    @GeneratedValue(generator = "permission_id_sequence")
    private Long id;
    private String code;
    private String name;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    private Module module;

    @Builder.Default
    @Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "permission")
    private List<Notification> notificationList = new ArrayList<>();

    @Builder.Default
    @Fetch(FetchMode.SELECT)
    @ManyToMany(mappedBy = "permissionList")
    private List<UserGroup> userGroupList = new ArrayList<>();

    @Builder.Default
    @Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "permission")
    private List<UserUserGroupPermission> userUserGroupPermissionList = new ArrayList<>();
}
