package mz.sisden.sisden.restapis.reportType.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReportTypeDTO {
    @NotBlank(message = "O nome do tipo de relatório é obrigatório")
    private String name;

    @NotNull(message = "O ID da instituição é obrigatório")
    private Long instituitionId;
}
