package mz.sisden.sisden.restapis.report.DTOs;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class LocationDTO {
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String description;
}
