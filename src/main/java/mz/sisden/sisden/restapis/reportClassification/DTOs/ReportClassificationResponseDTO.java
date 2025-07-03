package mz.sisden.sisden.restapis.reportClassification.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReportClassificationResponseDTO {
    private Long id;
    private String gravity;
    private String createdAt;
}
