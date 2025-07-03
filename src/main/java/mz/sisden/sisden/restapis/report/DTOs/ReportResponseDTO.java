package mz.sisden.sisden.restapis.report.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReportResponseDTO {
    private Long id;
    private String name;
    private List<String> phoneNumbers;
    private String email;
    private String title;
    private String description;
    private String status;
    private String createdAt;
    private String instituitionName;
    private String reportTypeName;
    private List<ReportFileResponseDTO> files;
    private LocationDTO location;
}



