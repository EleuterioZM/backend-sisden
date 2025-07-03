/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import mz.sisden.sisden.configuration.BaseEntity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

@Table(name = "user_vs_user_group_vs_permission")
@Getter
@Setter
@Entity
@Audited
@AuditOverride
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class UserUserGroupPermission extends BaseEntity {

    @Id
    @SequenceGenerator(name = "user_vs_user_group_vs_permission_id_sequence", allocationSize = 1)
    @GeneratedValue(generator = "user_vs_user_group_vs_permission_id_sequence")
    private Long id;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    private User user;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    private Permission permission;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    private UserGroup userGroup;
}
