package mz.sisden.sisden.restapis.report.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ReportDTO {

    @NotBlank(message = "O campo 'name' é obrigatório")
    private String name;

    private List<@NotBlank(message = "Telefone não pode estar vazio") String> phoneNumbers;

    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "O campo 'title' é obrigatório")
    private String title;

    private String description;

    @NotNull(message = "O campo 'instituitionId' é obrigatório")
    private Long instituitionId;

    @NotNull(message = "O campo 'reportTypeId' é obrigatório")
    private Long reportTypeId;

    private List<@NotNull FileDTO> files;

    private LocationDTO location;
}

