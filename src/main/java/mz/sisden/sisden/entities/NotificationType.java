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
public class NotificationType extends BaseEntity {
    @Id
    @SequenceGenerator(name = "notification_type_id_sequence", allocationSize = 1)
    @GeneratedValue(generator = "notification_type_id_sequence")
    private Long id;
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "notificationType")
    private List<Notification> NotificationList = new ArrayList<>();
}
