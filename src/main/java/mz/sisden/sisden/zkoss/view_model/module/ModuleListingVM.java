package mz.sisden.sisden.zkoss.view_model.module;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.configuration.ZkComponent;
import mz.sisden.sisden.entities.Module;
import mz.sisden.sisden.zkoss.ZkUtils;
import mz.sisden.sisden.zkoss.ZkPage;
import mz.sisden.sisden.zkoss.ZkArgument;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.zul.ListModelList;
import java.util.List;

@Slf4j
@Getter
@Setter
@ZkComponent
public class ModuleListingVM extends mz.sisden.sisden.zkoss.ZkViewModel {
    private ListModelList<Module> moduleListModel;

    @Init(superclass = true)
    public void init() {
    }

    @AfterCompose(superclass = true)
    public void afterCompose() {
        try {
            List<Module> modules = this.getBeans().getModuleService().findAll();
            moduleListModel = new ListModelList<>(modules);
        } catch (Exception e) {
            log.error("Erro ao inicializar ModuleListingVM", e);
            moduleListModel = new ListModelList<>();
        }
    }

    @Command
    public void addModule() {
        ZkUtils.getModalBuilder()
                .parentComponent(this.getComponent())
                .zkPage(ZkPage.MODULE_FORM)
                .onClose(event -> refreshList());
    }

    @Command
    @NotifyChange("moduleListModel")
    public void delete(@BindingParam("id") Long id) {
        org.zkoss.zul.Messagebox.show("Tem certeza que deseja remover este módulo?", "Confirmação", new org.zkoss.zul.Messagebox.Button[]{org.zkoss.zul.Messagebox.Button.YES, org.zkoss.zul.Messagebox.Button.NO}, org.zkoss.zul.Messagebox.QUESTION, event -> {
            if (org.zkoss.zul.Messagebox.ON_YES.equals(event.getName())) {
                try {
                    this.getBeans().getModuleService().delete(id);
                    org.zkoss.zul.Messagebox.show("Módulo removido com sucesso!", "Sucesso", org.zkoss.zul.Messagebox.OK, org.zkoss.zul.Messagebox.INFORMATION, evt -> {
                        refreshList();
                        org.zkoss.bind.BindUtils.postNotifyChange(null, null, this, "moduleListModel");
                    });
                } catch (Exception e) {
                    org.zkoss.zul.Messagebox.show("Não é possível remover o módulo porque ele está em uso em permissões.", "Erro", org.zkoss.zul.Messagebox.OK, org.zkoss.zul.Messagebox.ERROR);
                }
            }
        });
    }

    @Command
    public void edit(@BindingParam("id") Long id) {
        Module module = this.getBeans().getModuleService().findById(id).orElse(null);
        if (module != null) {
            ZkUtils.getModalBuilder()
                    .parentComponent(this.getComponent())
                    .zkPage(ZkPage.MODULE_FORM)
                    .fromZkPage(ZkPage.MODULE_LIST)
                    .withArgument(ZkArgument.MODULE, module)
                    .update()
                    .onClose(event -> refreshList());
        }
    }

    @Command
    public void view(@BindingParam("id") Long id) {
        Module module = this.getBeans().getModuleService().findById(id).orElse(null);
        if (module != null) {
            ZkUtils.getModalBuilder()
                    .parentComponent(this.getComponent())
                    .zkPage(ZkPage.MODULE_FORM)
                    .fromZkPage(ZkPage.MODULE_LIST)
                    .withArgument(ZkArgument.MODULE, module)
                    .readonly()
                    .onClose(event -> refreshList());
        }
    }

    @org.zkoss.bind.annotation.GlobalCommand
    @NotifyChange("moduleListModel")
    public void refreshList() {
        List<Module> modules = this.getBeans().getModuleService().findAll();
        moduleListModel = new ListModelList<>(modules);
    }
} 