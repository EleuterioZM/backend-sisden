package mz.sisden.sisden.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import mz.sisden.sisden.configuration.BaseEntity;
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
public class ReportClassification extends BaseEntity {

    @Id
    @SequenceGenerator(name = "report_classification_id_sequence", allocationSize = 1)
    @GeneratedValue(generator = "report_classification_id_sequence")
    private Long id;
    private String gravity;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
