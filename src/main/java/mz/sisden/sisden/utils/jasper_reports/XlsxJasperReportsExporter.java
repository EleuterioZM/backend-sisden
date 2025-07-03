/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.utils.jasper_reports;

import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.utils.ReportFormat;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

import java.io.ByteArrayOutputStream;

@Slf4j
public class XlsxJasperReportsExporter {
    private static final ReportFormat JASPER_REPORTS_EXPORTER = ReportFormat.XLSX;

    public byte[] build(
            JasperPrint jasperPrint,
            JrMetadata jrMetadata
    ) throws JRException {

        final JRXlsxExporter jrXlsxExporter = new JRXlsxExporter();

        final SimpleExporterInput simpleExporterInput = new SimpleExporterInput(jasperPrint);
        jrXlsxExporter.setExporterInput(simpleExporterInput);

        final SimpleXlsxExporterConfiguration simpleXlsxExporterConfiguration = new SimpleXlsxExporterConfiguration();
        simpleXlsxExporterConfiguration.setMetadataAuthor(jrMetadata.getMetadataAuthor());
        simpleXlsxExporterConfiguration.setMetadataTitle(jrMetadata.getMetadataTitle());
        simpleXlsxExporterConfiguration.setMetadataApplication(jrMetadata.getMetadataApplication());
        simpleXlsxExporterConfiguration.setMetadataKeywords(jrMetadata.getMetadataKeywords());
        simpleXlsxExporterConfiguration.setMetadataSubject(jrMetadata.getMetadataSubject());
        jrXlsxExporter.setConfiguration(simpleXlsxExporterConfiguration);

        final SimpleXlsxReportConfiguration simpleXlsxReportConfiguration = new SimpleXlsxReportConfiguration();
        simpleXlsxReportConfiguration.setOnePagePerSheet(jrMetadata.getIsOnePagePerSheet());
        simpleXlsxReportConfiguration.setRemoveEmptySpaceBetweenRows(jrMetadata.getIsRemoveEmptySpaceBetweenRows());
        simpleXlsxReportConfiguration.setRemoveEmptySpaceBetweenColumns(jrMetadata.getIsRemoveEmptySpaceBetweenColumns());
        simpleXlsxReportConfiguration.setDetectCellType(jrMetadata.getIsDetectCellType());
        simpleXlsxReportConfiguration.setIgnorePageMargins(jrMetadata.getIsIgnorePageMargins());
        simpleXlsxReportConfiguration.setProgressMonitor(jrMetadata.get(simpleXlsxReportConfiguration, log));
        jrXlsxExporter.setConfiguration(simpleXlsxReportConfiguration);

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final SimpleOutputStreamExporterOutput simpleOutputStreamExporterOutput = new SimpleOutputStreamExporterOutput(byteArrayOutputStream);
        jrXlsxExporter.setExporterOutput(simpleOutputStreamExporterOutput);

        jrXlsxExporter.exportReport();

        return byteArrayOutputStream.toByteArray();
    }
}
