/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.utils.jasper_reports;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.utils.ReportFormat;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import static java.util.Objects.isNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class JasperReportService {
    private final DataSource dataSource;

    public byte[] build(
            byte[] templateByteData,
            Map<String, Object> parameters,
            JRDataSource jrDataSource,
            JrMetadata metaData,
            ReportFormat reportFormat
    ) throws JRException {

        ByteArrayInputStream templateByteArrayInputStream = new ByteArrayInputStream(templateByteData);

        JasperPrint jasperPrint;
        if (isNull(jrDataSource)) {
            try (Connection connection = dataSource.getConnection()) {
                jasperPrint = JasperFillManager.fillReport(
                        templateByteArrayInputStream, parameters,
                        connection
                );
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            jasperPrint = JasperFillManager.fillReport(
                    templateByteArrayInputStream,
                    parameters,
                    jrDataSource
            );
        }

        final JRPdfExporter jrPdfExporter = new JRPdfExporter();

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (isNull(reportFormat) || ReportFormat.PDF.equals(reportFormat)) {
            final SimpleExporterInput simpleExporterInput = new SimpleExporterInput(jasperPrint);
            jrPdfExporter.setExporterInput(simpleExporterInput);

            final SimplePdfExporterConfiguration simplePdfExporterConfiguration = new SimplePdfExporterConfiguration();
            simplePdfExporterConfiguration.setMetadataAuthor(metaData.getMetadataAuthor());
            simplePdfExporterConfiguration.setMetadataTitle(metaData.getMetadataTitle());
            simplePdfExporterConfiguration.setMetadataCreator(metaData.getMetadataCreator());
            simplePdfExporterConfiguration.setMetadataKeywords(metaData.getMetadataKeywords());
            simplePdfExporterConfiguration.setMetadataSubject(metaData.getMetadataSubject());
            simplePdfExporterConfiguration.setDisplayMetadataTitle(true);
            jrPdfExporter.setConfiguration(simplePdfExporterConfiguration);

            final SimpleOutputStreamExporterOutput simpleOutputStreamExporterOutput = new SimpleOutputStreamExporterOutput(byteArrayOutputStream);
            jrPdfExporter.setExporterOutput(simpleOutputStreamExporterOutput);
        }

        jrPdfExporter.exportReport();

        return byteArrayOutputStream.toByteArray();
    }
}
