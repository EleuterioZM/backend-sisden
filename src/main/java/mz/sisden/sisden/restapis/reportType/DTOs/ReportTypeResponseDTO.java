package mz.sisden.sisden.restapis.reportType.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReportTypeResponseDTO {
    private Long id;
    private String name;
    private String createdAt;
    private Long instituitionId;
    private String instituitionName;
}
