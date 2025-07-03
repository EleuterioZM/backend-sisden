package mz.sisden.sisden.zkoss.view_model.report_classification;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.configuration.ZkComponent;
import mz.sisden.sisden.entities.ReportClassification;
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
public class ReportClassificationListingVM extends ZkViewModel {

    private ReportClassificationListModel reportClassificationListModel;
    private List<CustomMap> selectedReportClassificationList = new ArrayList<>();
    private String searchTerm;

    @Init(superclass = true)
    public void init() {
    }

    @AfterCompose(superclass = true)
    public void afterCompose() {
        this.reportClassificationListModel = new ReportClassificationListModel(this.searchTerm);

        List<ReportClassification> reportClassificationList =
                ZkUtils.getAttribute(ZkArgument.REPORT_CLASSIFICATION_LIST.name());
        if (CollectionUtils.isNotEmpty(reportClassificationList)) {
            reportClassificationList.stream()
                    .map(ReportClassification::getId)
                    .peek(this.reportClassificationListModel.getPriorityIds()::add)
                    .map(CustomMap::ofId)
                    .forEach(this.selectedReportClassificationList::add);
        }
    }

    @Command
    public void search() {
        this.reportClassificationListModel = new ReportClassificationListModel(this.searchTerm);

        selectedReportClassificationList.stream()
                .map(CustomMap::getId)
                .forEach(this.reportClassificationListModel.getPriorityIds()::add);

        BindUtils.postNotifyChange(this, "reportClassificationListModel");
    }

    @Command
    public void edit(@BindingParam("id") Long id) {
        ZkUtils.getModalBuilder()
                .parentComponent(this.getComponent())
                .zkPage(ZkPage.REPORT_CLASSIFICATION_FORM)
                .withArgument(ZkArgument.REPORT_CLASSIFICATION,
                        this.getBeans().getReportClassificationRepository().findById(id).orElseThrow())
                .update()
                .onClose(event -> {
                    this.reportClassificationListModel = new ReportClassificationListModel(this.searchTerm);
                    BindUtils.postNotifyChange(this, "reportClassificationListModel");
                });
    }

    @Command
    public void delete(@BindingParam("id") Long id) {
        ReportClassification reportClassification = this.getBeans()
                .getReportClassificationRepository().findById(id).orElseThrow();

        ZkAlerts.builder()
                .question("Deseja apagar a classificação de denúncia: {}?", reportClassification.getGravity())
                .onYesListener(event -> {
                    this.getBeans().getReportClassificationRepository().delete(reportClassification);
                    this.reportClassificationListModel = new ReportClassificationListModel(this.searchTerm);
                    BindUtils.postNotifyChange(this, "reportClassificationListModel");
                })
                .show();
    }

    @Command
    public void select() {
        Map<String, Object> arguments = ZkArgument.REPORT_CLASSIFICATION.put(this.selectedReportClassificationList.get(0));
        ZkArgument.REPORT_CLASSIFICATION_LIST.putIn(arguments, this.selectedReportClassificationList);
        ZkArgument.REPORT_CLASSIFICATION_ARRAY.putIn(arguments, this.selectedReportClassificationList.toArray(new ReportClassification[0]));

        ZkEvent.onReportClassificationSelected.post(this.getComponent(), arguments);
        ZkUtils.back(this.getComponent());
    }
}
