package mz.sisden.sisden.entities;

import jakarta.persistence.*;
import lombok.*;
import mz.sisden.sisden.configuration.BaseEntity;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(length = 500)
    private String description;

    @OneToOne
    @JoinColumn(name = "report_id", unique = true)
    private Report report;
}
