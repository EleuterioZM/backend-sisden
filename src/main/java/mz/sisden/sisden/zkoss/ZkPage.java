/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.zkoss;

import lombok.Getter;

import java.util.Optional;
import java.util.stream.Stream;

@Getter
public enum ZkPage {
    HOME("home", "", ""),
    NOTIFICATION("notification", "", ""),
    SYSTEM_FILE_VIEWER("viewers/SystemFileViewer", "", ""),

    USER_FORM("user/user-form", "", ""),
    USER_LIST("user/user-listing", "", ""),

    USER_GROUP_FORM("user-group/user-group-form", "", ""),
    USER_GROUP_LIST("user-group/user-group-listing", "", ""),

    REPORT_TYPE_FORM("parameterization/report_type/report-type-form","",""),
    REPORT_TYPE_LIST("parameterization/report_type/report-type-lst","",""),

    REPORT_CLASSIFICATION_FORM("parameterization/report_classification/report-classification-form","",""),
    REPORT_CLASSIFICATION_LIST("parameterization/report_classification/report-classification-listing","",""),

    INSTITUITION_FORM("parameterization/instituition/instituition-form","",""),
    INSTITUITION_LIST("parameterization/instituition/instituition-listing","",""),

    TEAM_FORM("parameterization/team/team-form","",""),
    TEAM_LIST("parameterization/team/team-listing","",""),

    REPORT_FORM("report/report-form","",""),
    REPORT_LIST("report/report-listing","",""),

    PERMISSION_FORM("parameterization/permission/permission-form", "", ""),
    PERMISSION_LIST("parameterization/permission/permission-listing", "", ""),
    
    PERMISSION_MANAGEMENT_FORM("parameterization/permission/permission-management-form", "", ""),
    PERMISSION_MANAGEMENT_LIST("parameterization/permission/permission-management-listing", "", ""),
    
    MODULE_FORM("parameterization/module/module-form", "", ""),
    MODULE_LIST("parameterization/module/module-listing", "", ""),
    ;

    private final String zulFile;
    private final String designation;
    private final String description;
//    private final List<ZkPage> children;

    ZkPage(String zulFile, String designation, String description) {
        this.zulFile = zulFile;
        this.designation = designation;
        this.description = description;
    }

    public static Optional<ZkPage> findByName(String name) {
        return Stream.of(ZkPage.values()).filter(zkPage -> zkPage.name().equals(name)).findFirst();
    }

    public String getZulFile() {
        if (this.zulFile.endsWith(".zul"))
            return this.zulFile;
        return zulFile + ".zul";
    }

    public String getPath() {
        return "/secured/".concat(this.getZulFile());
    }
}
