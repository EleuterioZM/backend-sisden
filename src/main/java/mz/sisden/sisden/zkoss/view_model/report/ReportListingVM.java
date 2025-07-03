package mz.sisden.sisden.zkoss.view_model.report;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.configuration.ZkComponent;
import mz.sisden.sisden.entities.Report;
import mz.sisden.sisden.utils.CustomMap;
import mz.sisden.sisden.zkoss.*;
import org.apache.commons.collections4.CollectionUtils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;

import java.util.*;

@Slf4j
@Getter
@Setter
@ZkComponent
public class ReportListingVM extends ZkViewModel {
    private ReportListModel reportListModel;
    private List<CustomMap> selectedReportList = new ArrayList<>();
    private String searchTerm;
    private String selectedStatus = "";

    @Init(superclass = true)
    public void init() {}

    @AfterCompose(superclass = true)
    public void afterCompose() {
        this.reportListModel = new ReportListModel(this.searchTerm, this.selectedStatus);

        List<Report> reportList = ZkUtils.getAttribute(ZkArgument.REPORT_LIST.name());
        if (CollectionUtils.isNotEmpty(reportList)) {
            reportList.stream()
                    .map(Report::getId)
                    .peek(this.reportListModel.getPriorityIds()::add)
                    .map(CustomMap::ofId)
                    .forEach(this.selectedReportList::add);
        }
    }

    @Command
    @NotifyChange({"reportListModel", "selectedStatus"})
    public void search() {
        this.reportListModel = new ReportListModel(this.searchTerm, this.selectedStatus);

        selectedReportList.stream()
                .map(CustomMap::getId)
                .forEach(this.reportListModel.getPriorityIds()::add);

        BindUtils.postNotifyChange(this, "reportListModel");
    }

    @Command
    public void edit(@BindingParam Long id) {
        ZkUtils.getModalBuilder()
                .parentComponent(this.getComponent())
                .zkPage(ZkPage.REPORT_FORM)
                .withArgument(ZkArgument.REPORT, this.getBeans().getReportRepository().findById(id).orElseThrow())
                .update()
                .onClose(event -> {
                    this.reportListModel = new ReportListModel(this.searchTerm, this.selectedStatus);
                    BindUtils.postNotifyChange(this, "reportListModel");
                });
    }

    @Command
    public void view(@BindingParam Long id) {
        ZkUtils.getModalBuilder()
                .parentComponent(this.getComponent())
                .zkPage(ZkPage.REPORT_FORM)
                .withArgument(ZkArgument.REPORT, this.getBeans().getReportRepository().findById(id).orElseThrow())
                .readonly()
                .show();
    }

    @Command
    public void delete(@BindingParam Long id) {
        Report report = this.getBeans().getReportRepository().findById(id).orElseThrow();
        ZkAlerts.builder()
                .question("Deseja apagar o relatório '{}'", report.getName())
                .onYesListener(event -> {
                    this.getBeans().getReportRepository().delete(report);
                    this.reportListModel = new ReportListModel(this.searchTerm, this.selectedStatus);
                    BindUtils.postNotifyChange(this, "reportListModel");
                })
                .show();
    }

    @Command
    public void select() {
        Map<String, Object> arguments = ZkArgument.REPORT.put(this.selectedReportList.get(0));
        ZkArgument.REPORT_LIST.putIn(arguments, this.selectedReportList);
        ZkArgument.REPORT_ARRAY.putIn(arguments, this.selectedReportList.toArray(new Report[0]));

        ZkEvent.onReportSelected.post(this.getComponent(), arguments);
        ZkUtils.back(this.getComponent());
    }
}
