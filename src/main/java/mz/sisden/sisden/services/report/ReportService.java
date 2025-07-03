package mz.sisden.sisden.services.report;


import lombok.RequiredArgsConstructor;
import mz.sisden.sisden.entities.*;
import mz.sisden.sisden.enums.Status;
import mz.sisden.sisden.repositories.*;
import mz.sisden.sisden.restapis.report.DTOs.LocationDTO;
import mz.sisden.sisden.restapis.report.DTOs.ReportDTO;
import mz.sisden.sisden.restapis.report.DTOs.ReportFileResponseDTO;
import mz.sisden.sisden.restapis.report.DTOs.ReportResponseDTO;
import org.springframework.stereotype.Service;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final TeamRepository teamRepository;
    private final InstituitionRepository instituitionRepository;
    private final ReportTypeRepository reportTypeRepository;

    @Transactional
    public Report createReportWithFiles(ReportDTO dto) {
        Instituition instituition = instituitionRepository.findById(dto.getInstituitionId())
                .orElseThrow(() -> new IllegalArgumentException("Instituição com ID " + dto.getInstituitionId() + " não encontrada"));

        ReportType reportType = reportTypeRepository.findById(dto.getReportTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de relatório com ID " + dto.getReportTypeId() + " não encontrado"));

        Report report = Report.builder()
                .name(dto.getName())
                .phoneNumbers(dto.getPhoneNumbers())
                .email(dto.getEmail())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(Status.OPEN)
                .instituition(instituition)
                .reportType(reportType)
                .build();

        if (Objects.nonNull(dto.getLocation())) {
            Location location = Location.builder()
                    .latitude(dto.getLocation().getLatitude())
                    .longitude(dto.getLocation().getLongitude())
                    .description(dto.getLocation().getDescription())
                    .report(report)  // associar para cascata funcionar
                    .build();
            report.setLocation(location);
        }

        if (Objects.nonNull(dto.getFiles()) && !dto.getFiles().isEmpty()) {
            List<ReportFile> reportFiles = dto.getFiles().stream()
                    .map(fileDTO -> {
                        try {
                            byte[] data = Base64.getDecoder().decode(fileDTO.getData());
                            return ReportFile.builder()
                                    .fileName(fileDTO.getFileName())
                                    .contentType(fileDTO.getContentType())
                                    .data(data)
                                    .report(report)
                                    .build();
                        } catch (IllegalArgumentException e) {
                            throw new IllegalArgumentException("Erro ao decodificar arquivo: " + fileDTO.getFileName());
                        }
                    })
                    .collect(Collectors.toList());

            report.setReportFileList(reportFiles);
        }

        return reportRepository.save(report);
    }


    public List<Report> getAll() {
        return reportRepository.findAll();
    }

    public Optional<Report> getById(Long id) {
        return reportRepository.findById(id);
    }

    public boolean existsById(Long id) {
        return reportRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public ReportResponseDTO toResponseDTO(Report report) {
        List<ReportFileResponseDTO> fileDTOs = report.getReportFileList().stream()
                .map(file -> new ReportFileResponseDTO(
                        file.getFileName(),
                        file.getContentType(),
                        Base64.getEncoder().encodeToString(file.getData())
                ))
                .toList();

        LocationDTO locationDTO = null;
        if (report.getLocation() != null) {
            locationDTO = new LocationDTO();
            locationDTO.setLatitude(report.getLocation().getLatitude());
            locationDTO.setLongitude(report.getLocation().getLongitude());
            locationDTO.setDescription(report.getLocation().getDescription());
        }

        return new ReportResponseDTO(
                report.getId(),
                report.getName(),
                report.getPhoneNumbers(),
                report.getEmail(),
                report.getTitle(),
                report.getDescription(),
                report.getStatus().name(),
                report.getCreatedAt() != null ? report.getCreatedAt().toString() : null,
                report.getInstituition().getName(),
                report.getReportType().getName(),
                fileDTOs,
                locationDTO
        );
    }

}
