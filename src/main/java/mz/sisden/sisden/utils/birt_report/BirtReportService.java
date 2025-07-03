/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.utils.birt_report;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.utils.ReportFormat;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class BirtReportService {
    private final Environment environment;
    private final RestTemplate restTemplate;

    public byte[] build(
            String xmlBase64,
            String templateBase64,
            ReportFormat format
    ) {
        String birtReportUrl = this.environment.getRequiredProperty("birt-report.url");
        String birtReportAlternativeUrl = this.environment.getRequiredProperty("birt-report.alternative-url");

        ReportRequest request = new ReportRequest();
        request.setXmlBase65(xmlBase64);
        request.setTemplateBase64(templateBase64);
        request.setReportFormat(format);


        try {
            RequestEntity<ReportRequest> requestEntity = new RequestEntity<>(
                    request,
                    HttpMethod.POST,
                    URI.create(birtReportUrl)
            );
            ResponseEntity<String> responseEntity = this.restTemplate.exchange(requestEntity, String.class);

            return Base64.getDecoder().decode(responseEntity.getBody());

        } catch (Exception e) {
            log.error("Failed to build birt report using url.", e);
        }

        try {
            RequestEntity<ReportRequest> requestEntity = new RequestEntity<>(
                    request,
                    HttpMethod.POST,
                    URI.create(birtReportAlternativeUrl)
            );
            ResponseEntity<String> responseEntity = this.restTemplate.exchange(requestEntity, String.class);

            return Base64.getDecoder().decode(responseEntity.getBody());
        } catch (Exception e) {
            log.error("Failed to build birt report using alternative url.", e);
        }

        return null;
    }

    @Data
    public static class ReportRequest {
        private ReportFormat reportFormat = ReportFormat.PDF;
        private String xmlBase65;
        private String templateBase64;
    }
}
