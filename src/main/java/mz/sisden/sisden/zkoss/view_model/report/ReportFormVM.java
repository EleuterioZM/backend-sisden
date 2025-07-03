package mz.sisden.sisden.zkoss.view_model.report;

import lombok.Getter;
import lombok.Setter;
import mz.sisden.sisden.configuration.ZkComponent;
import mz.sisden.sisden.entities.*;
import mz.sisden.sisden.enums.Status;
import mz.sisden.sisden.zkoss.ZkAlerts;
import mz.sisden.sisden.zkoss.ZkArgument;
import mz.sisden.sisden.zkoss.ZkUtils;
import mz.sisden.sisden.zkoss.ZkViewModel;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@Setter
@ZkComponent
public class ReportFormVM extends ZkViewModel {

    private Report report;
    private List<ReportType> reportTypeList;
    private ReportType selectedReportType;

    private List<Status> statusList = Arrays.asList(Status.values());
    private Status selectedStatus;

    private String phoneNumbersString;

    @Wire
    Div divImagens;

    @Command
    public void openImage(@BindingParam Media media){
        SystemFile systemFile = new SystemFile();
        systemFile.setFileName(media.getName());
        systemFile.setByteData(media.getByteData());
        ZkUtils.showFile(this.getComponent(), systemFile);
    }

    @Init(superclass = true)
    public void init() {}

    @AfterCompose(superclass = true)
    public void afterCompose() throws IOException {
        this.report = ZkUtils.getAttribute(ZkArgument.REPORT.name());

        this.reportTypeList = this.getBeans().getReportTypeRepository().findAll();

        if (this.report != null) {
            this.selectedReportType = this.report.getReportType();
            this.selectedStatus = this.report.getStatus();

            List<String> phones = this.report.getPhoneNumbers();
            if (CollectionUtils.isNotEmpty(phones)) {
                this.phoneNumbersString = String.join(", ", phones);
            } else {
                this.phoneNumbersString = "";
            }

            List<ReportFile> reportFileList = this.report.getReportFileList();

            for(ReportFile rf : reportFileList){
                byte[] data = rf.getData();
                String fileName = rf.getFileName();

                if(ArrayUtils.isEmpty(data)){
                    continue;
                }

                AImage aImage = new AImage(fileName, data);
                Image image = new Image();
                image.setContent(aImage);
                image.setStyle("display: block; max-width: 100%; height: 100%;");
                image.addEventListener(Events.ON_CLICK, event -> {
                    SystemFile file = new SystemFile();
                    file.setByteData(data);
                    file.setFileName(fileName);
                    ZkUtils.showFile(this.getComponent(), file);
                });
                this.divImagens.appendChild(image);
            }

            if (Objects.isNull(this.report.getLocation())) {
                this.report.setLocation(new Location());
                this.report.getLocation().setReport(this.report);
            }
        }

        if (Objects.isNull(this.report)) {
            this.report = new Report();
        }
    }

    @Command
    public void saveOrUpdate() {
        if (this.getCreate()) {
            saveOrUpdate("Criar", "Criada");
        } else if (this.getUpdate()) {
            saveOrUpdate("Actualizar", "Actualizada");
        }
    }

    private void saveOrUpdate(String action, String actionPast) {
        ZkAlerts.builder()
                .question("Deseja " + action + " a Denúncia {}?", this.report.getName())
                .onYesListener(event -> {

                    this.report.setReportType(selectedReportType);
                    this.report.setStatus(selectedStatus);

                    List<String> phones = Arrays.stream(phoneNumbersString.split("\\s*,\\s*"))
                            .filter(s -> !s.isEmpty())
                            .collect(Collectors.toList());
                    this.report.setPhoneNumbers(phones);

                    this.getBeans().getReportRepository().save(this.report);

                    ZkAlerts.info("Denúncia " + actionPast + " com sucesso!");
                    ZkUtils.back(this.getComponent());
                }).show();
    }
}
