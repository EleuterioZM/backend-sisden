package mz.sisden.sisden.zkoss.view_model.report_classification;

import lombok.Getter;
import lombok.Setter;
import mz.sisden.sisden.configuration.ZkComponent;
import mz.sisden.sisden.entities.ReportClassification;
import mz.sisden.sisden.zkoss.ZkAlerts;
import mz.sisden.sisden.zkoss.ZkArgument;
import mz.sisden.sisden.zkoss.ZkUtils;
import mz.sisden.sisden.zkoss.ZkViewModel;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;

import java.util.Objects;

@Getter
@Setter
@ZkComponent
public class ReportClassificationFormVM extends ZkViewModel {

    private ReportClassification reportClassification;

    @Init(superclass = true)
    public void init() {
    }

    @AfterCompose(superclass = true)
    public void afterCompose() {
        this.reportClassification = ZkUtils.getAttribute(ZkArgument.REPORT_CLASSIFICATION.name());

        if (Objects.isNull(this.reportClassification)) {
            this.reportClassification = new ReportClassification();
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

    public void saveOrUpdate(String message, String alertInfo) {
        ZkAlerts.builder()
                .question("Deseja " + message + " a Classificação de Denúncia {}?", this.reportClassification.getGravity())
                .onYesListener(event -> {
                    this.getBeans().getReportClassificationRepository().save(this.reportClassification);
                    ZkAlerts.info("Classificação de Denúncia " + alertInfo + " com sucesso!");
                    ZkUtils.back(this.getComponent());
                })
                .show();
    }
}
