/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.utils.jasper_reports;

import lombok.Builder;
import lombok.Data;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.export.JRExportProgressMonitor;
import net.sf.jasperreports.engine.export.type.ImageAnchorTypeEnum;
import net.sf.jasperreports.engine.type.RunDirectionEnum;
import net.sf.jasperreports.export.SimpleReportExportConfiguration;
import net.sf.jasperreports.export.type.PdfPrintScalingEnum;
import net.sf.jasperreports.export.type.PdfVersionEnum;
import net.sf.jasperreports.export.type.PdfaConformanceEnum;
import org.slf4j.Logger;

import java.awt.*;
import java.util.Map;

@Data
@Builder(toBuilder = true)
public class JrMetadata {

    private String metadataTitle;
    private String metadataAuthor;
    private String metadataSubject;
    private String metadataKeywords;
    //PDF
    private String metadataCreator;
    private Boolean isCompressed;
    private Boolean isEncrypted;
    private Boolean is128BitKey;
    private String userPassword;
    private String ownerPassword;
    private PdfVersionEnum pdfVersion;
    private String pdfJavaScript;
    private PdfPrintScalingEnum printScaling;
    private Boolean isTagged;
    private String tagLanguage;
    private PdfaConformanceEnum pdfaConformance;
    private String iccProfilePath;
    private Integer permissions;
    private String allowedPermissionsHint;
    private String deniedPermissionsHint;
    //XLSX
    private Boolean isCreateCustomPalette;
    private String workbookTemplate;
    private Boolean isKeepWorkbookTemplateSheets;
    private String metadataApplication;
    private Boolean isOnePagePerSheet;
    private Boolean isRemoveEmptySpaceBetweenRows;
    private Boolean isRemoveEmptySpaceBetweenColumns;
    private Boolean isWhitePageBackground;
    private Boolean isDetectCellType;
    private Boolean isFontSizeFixEnabled;
    private Boolean isImageBorderFixEnabled;
    private Boolean isIgnoreGraphics;
    private Boolean isCollapseRowSpan;
    private Boolean isIgnoreCellBorder;
    private Boolean isIgnoreCellBackground;
    private Boolean isWrapText;
    private Boolean isCellLocked;
    private Boolean isCellHidden;
    private Integer maxRowsPerSheet;
    private Boolean isIgnorePageMargins;
    private String sheetHeaderLeft;
    private String sheetHeaderCenter;
    private String sheetHeaderRight;
    private String sheetFooterLeft;
    private String sheetFooterCenter;
    private String sheetFooterRight;
    private String password;
    private String[] sheetNames;
    private Map<String, String> formatPatternsMap;
    private Boolean isIgnoreHyperlink;
    private Boolean isIgnoreAnchors;
    private Integer fitWidth;
    private Integer fitHeight;
    private Integer pageScale;
    private RunDirectionEnum sheetDirection;
    private Float columnWidthRatio;
    private Boolean isUseTimeZone;
    private Integer firstPageNumber;
    private Boolean isShowGridLines;
    private ImageAnchorTypeEnum imageAnchorType;
    private Boolean isAutoFitPageHeight;
    private Boolean isForcePageBreaks;
    private Boolean isShrinkToFit;
    private Boolean isIgnoreTextFormatting;
    private Color sheetTabColor;
    private Integer freezeRow;
    private String freezeColumn;
    private Integer printPageTopMargin;
    private Integer printPageLeftMargin;
    private Integer printPageBottomMargin;
    private Integer printPageRightMargin;
    private Integer printPageHeight;
    private Integer printPageWidth;
    private Integer printHeaderMargin;
    private Integer printFooterMargin;
    private JRPropertiesUtil.PropertySuffix[] definedNames;

    //COMMON
    public JRExportProgressMonitor get(SimpleReportExportConfiguration simpleReportExportConfiguration, Logger logger) {
        return () -> {
            final Integer startPageIndex = simpleReportExportConfiguration.getStartPageIndex();
            final Integer pageIndex = simpleReportExportConfiguration.getPageIndex();
            final Integer endPageIndex = simpleReportExportConfiguration.getEndPageIndex();

            logger.debug("Page {} exported of {} pages.", pageIndex, endPageIndex);
        };
    }
}
