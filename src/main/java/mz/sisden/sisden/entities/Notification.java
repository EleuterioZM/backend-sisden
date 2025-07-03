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

import java.time.LocalDateTime;

@Table
@Getter
@Setter
@Entity
@Audited
@AuditOverride
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Notification extends BaseEntity {
    @Id
    @SequenceGenerator(name = "notification_id_sequence", allocationSize = 1)
    @GeneratedValue(generator = "notification_id_sequence")
    private Long id;

    private String title;
    private String description;

    @Builder.Default
    private Boolean viewed = Boolean.FALSE;
    private LocalDateTime viewedAt;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    private User userViewer;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    private NotificationType notificationType;

    //grupo de utilizadores que pode ver
    @ManyToOne
    @Fetch(FetchMode.SELECT)
    private UserGroup userGroup;

    //Utilizadores com esta permissão podem ver.
    @ManyToOne
    @Fetch(FetchMode.SELECT)
    private Permission permission;

    //can pointer to null entity.
    private Long memberId;
    private Long householdId;
}
