package mz.sisden.sisden.zkoss.view_model.report_type;

import lombok.Getter;
import lombok.Setter;
import mz.sisden.sisden.configuration.ZkComponent;
import mz.sisden.sisden.entities.Instituition;
import mz.sisden.sisden.entities.ReportType;
import mz.sisden.sisden.zkoss.ZkAlerts;
import mz.sisden.sisden.zkoss.ZkArgument;
import mz.sisden.sisden.zkoss.ZkUtils;
import mz.sisden.sisden.zkoss.ZkViewModel;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ZkComponent
public class ReportTypeFormVM extends ZkViewModel {
    private ReportType reportType;

    private List<Instituition> instituitionListModel;
    private Instituition selectedInstituition;


    @Init(superclass = true)
    public void init() {
        this.instituitionListModel = getBeans().getInstituitionRepository().findAll();
    }

    @AfterCompose(superclass = true)
    public void afterCompose() {
        this.reportType = ZkUtils.getAttribute(ZkArgument.REPORT_TYPE.name());

        if (Objects.isNull(this.reportType)) {
            this.reportType = new ReportType();
        } else {
            this.selectedInstituition = this.reportType.getInstituition();
        }
    }

    @Command
    public void selectInstituition() {
        this.reportType.setInstituition(selectedInstituition);
    }

    @Command
    public void saveOrUpdate() {
        if (this.getCreate()) {
            saveOrUpdate("Criar", "Criada");
        } else if (this.getUpdate()) {
            saveOrUpdate("Actualizar", "Actualizada");
        }
    }

    public void saveOrUpdate(String message, String alertInfo) {
        ZkAlerts.builder()
                .question("Deseja " + message + " o Tipo de Denúncia {}", this.reportType.getName())
                .onYesListener(event -> {
                    this.getBeans().getReportTypeRepository().save(this.reportType);
                    ZkAlerts.info("Tipo de Denúncia " + alertInfo + " com sucesso!");
                    ZkUtils.back(this.getComponent());
                }).show();
    }
}
