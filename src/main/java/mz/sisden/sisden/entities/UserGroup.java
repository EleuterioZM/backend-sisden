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
public class UserGroup extends BaseEntity {
    @Id
    @SequenceGenerator(name = "user_group_id_sequence", allocationSize = 1)
    @GeneratedValue(generator = "user_group_id_sequence")
    private Long id;
    private String code;
    private String name;
    private String description;

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "userGroup")
    private List<UserUserGroupPermission> userUserGroupPermissionList = new ArrayList<>();

    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "user_group_vs_permission",
            joinColumns = {@JoinColumn(name = "user_group_id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id")}
    )
    private List<Permission> permissionList = new ArrayList<>();
}
