package mz.sisden.sisden.zkoss.view_model.report_type;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.configuration.ZkComponent;
import mz.sisden.sisden.entities.ReportType;
import mz.sisden.sisden.utils.CustomMap;
import mz.sisden.sisden.zkoss.*;
import org.apache.commons.collections4.CollectionUtils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;

import java.util.*;

@Slf4j
@Getter
@Setter
@ZkComponent
public class ReportTypeListingVM extends ZkViewModel {
    ReportTypeListModel reportTypeListModel;
    List<CustomMap> selectedReportTypeList = new ArrayList<>();
    String searchTerm;

    @Init(superclass = true)
    public void init() {
    }

    @AfterCompose(superclass = true)
    public void afterCompose() {
        this.reportTypeListModel = new ReportTypeListModel(this.searchTerm);

        List<ReportType> reportTypeList = ZkUtils.getAttribute(ZkArgument.REPORT_TYPE_LIST.name());
        if(CollectionUtils.isNotEmpty(reportTypeList)){
            reportTypeList.stream()
                    .map(ReportType::getId)
                    .peek(this.reportTypeListModel.getPriorityIds()::add)
                    .map(CustomMap::ofId)
                    .forEach(this.selectedReportTypeList::add);
        }
    }

    @Command
    public void search() {
        this.reportTypeListModel = new ReportTypeListModel(this.searchTerm);

        selectedReportTypeList.stream()
                        .map(CustomMap::getId)
                        .forEach(this.reportTypeListModel.getPriorityIds()::add);

        BindUtils.postNotifyChange(this, "reportTypeListModel");
    }

    @Command
    public void edit(@BindingParam Long id) {
        ZkUtils.getModalBuilder()
                .parentComponent(this.getComponent())
                .zkPage(ZkPage.REPORT_TYPE_FORM)
                .withArgument(ZkArgument.REPORT_TYPE, this.getBeans().getReportTypeRepository().findById(id).orElseThrow())
                .update()
                .onClose(event -> {
                    this.reportTypeListModel = new ReportTypeListModel(this.searchTerm);
                    BindUtils.postNotifyChange(this, "reportTypeListModel");
                });
    }

    @Command
    public void delete(@BindingParam Long id) {
        ReportType reportType = this.getBeans().getReportTypeRepository().findById(id).orElseThrow();
        ZkAlerts.builder()
                .question("Deseja apagar o tipo de Denúnica{}?", reportType.getName())
                .onYesListener(event -> {
                    this.getBeans().getReportTypeRepository().delete(reportType);
                    this.reportTypeListModel = new ReportTypeListModel(this.searchTerm);
                    BindUtils.postNotifyChange(this, "reportTypeListModel");
                })
                .show();
    }

    @Command
    public void select() {
        Map<String, Object> arguments = ZkArgument.REPORT_TYPE.put(this.selectedReportTypeList.get(0));
        ZkArgument.REPORT_TYPE_LIST.putIn(arguments, this.selectedReportTypeList);
        ZkArgument.REPORT_TYPE_ARRAY.putIn(arguments, this.selectedReportTypeList.toArray(new ReportType[0]));

        ZkEvent.onReportTypeSelected.post(this.getComponent(), arguments);
        ZkUtils.back(this.getComponent());
    }

}
