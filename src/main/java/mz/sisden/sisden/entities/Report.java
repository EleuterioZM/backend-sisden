package mz.sisden.sisden.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import mz.sisden.sisden.configuration.BaseEntity;
import mz.sisden.sisden.enums.Status;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

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
public class Report extends BaseEntity {

    @Id
    @SequenceGenerator(name = "report_id_sequence", allocationSize = 1)
    @GeneratedValue(generator = "report_id_sequence")
    private Long id;

    private String name;

    @ElementCollection
    @CollectionTable(name = "report_phone_numbers", joinColumns = @JoinColumn(name = "report_id"))
    @Column(name = "phone_number")
    private List<String> phoneNumbers;

    private String email;

    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "responsible_team_id")
    private Team responsibleTeam;

    @ManyToOne
    @JoinColumn(name = "instituition_id")
    private Instituition instituition;

    @ManyToOne
    @JoinColumn(name = "report_type_id")
    private ReportType reportType;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportFile> reportFileList;

    @OneToOne(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    private Location location;
}
