package mz.sisden.sisden.restapis.report.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportFileResponseDTO {
    private String fileName;
    private String contentType;
    private String base64Data;
}
