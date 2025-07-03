package mz.sisden.sisden.restapis.report.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FileDTO {

    @NotBlank(message = "O nome do ficheiro é obrigatório")
    private String fileName;

    @NotBlank(message = "O tipo de conteúdo é obrigatório")
    private String contentType;

    @NotBlank(message = "Os dados do ficheiro (base64) são obrigatórios")
    private String data; // base64 string
}
