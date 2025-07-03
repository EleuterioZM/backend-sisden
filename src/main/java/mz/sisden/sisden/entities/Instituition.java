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
public class Instituition extends BaseEntity {

    @Id
    @SequenceGenerator(name = "instituition_id_sequence", allocationSize = 1)
    @GeneratedValue(generator = "instituition_id_sequence")
    private Long id;

    private String name;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @ManyToMany
    @JoinTable(
            name = "instituition_vs_user",
            joinColumns = @JoinColumn(name = "instituition_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    @OneToMany(mappedBy = "instituition")
    private List<Report> reports;

    @OneToMany(mappedBy = "instituition")
    private List<ReportType> reportTypes;
}
