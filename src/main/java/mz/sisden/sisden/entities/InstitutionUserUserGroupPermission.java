package mz.sisden.sisden.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import mz.sisden.sisden.configuration.BaseEntity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

@Table(name = "institution_user_vs_user_group_vs_permission")
@Getter
@Setter
@Entity
@Audited
@AuditOverride
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class InstitutionUserUserGroupPermission extends BaseEntity {
    @Id
    @SequenceGenerator(name = "institution_user_vs_user_group_vs_permission_id_sequence", allocationSize = 1)
    @GeneratedValue(generator = "institution_user_vs_user_group_vs_permission_id_sequence")
    private Long id;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    private Instituition instituition;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    private User user;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    private UserGroup userGroup;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    private Permission permission;
} 