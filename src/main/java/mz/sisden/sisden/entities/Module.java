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
public class Module extends BaseEntity {
    @Id
    @SequenceGenerator(name = "module_id_sequence", allocationSize = 1)
    @GeneratedValue(generator = "module_id_sequence")
    private Long id;
    private String name;
    private String code;
    private String description;

    @Builder.Default
    @OneToMany(mappedBy = "module")
    private List<Permission> permissionList = new ArrayList<>();
}
