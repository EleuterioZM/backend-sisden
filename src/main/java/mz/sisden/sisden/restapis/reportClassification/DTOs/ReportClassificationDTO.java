package mz.sisden.sisden.restapis.reportClassification.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReportClassificationDTO {
    @NotBlank(message = "O campo gravity é obrigatório")
    private String gravity;
}
