package mz.sisden.sisden.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import mz.sisden.sisden.configuration.BaseEntity;
import org.hibernate.envers.Audited;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class ReportFile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String contentType;

    //todo: salvar no sistema de ficheiro, se não a base de dados ficará muito, mas muito grande e backups serão custosos.
    @Lob
    @Basic(fetch = FetchType.EAGER)
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "report_id")
    private Report report;
}

