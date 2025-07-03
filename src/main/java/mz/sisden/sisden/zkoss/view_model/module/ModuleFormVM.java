package mz.sisden.sisden.zkoss.view_model.module;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.entities.Module;
import mz.sisden.sisden.services.module.ModuleService;
import org.zkoss.bind.annotation.*;
import org.zkoss.zul.Messagebox;
import mz.sisden.sisden.zkoss.ZkViewModel;
import mz.sisden.sisden.zkoss.ZkUtils;
import mz.sisden.sisden.configuration.ZkComponent;

@Slf4j
@Getter
@Setter
@ZkComponent
public class ModuleFormVM extends ZkViewModel {
    private Module module;

    private boolean create;
    private boolean update;
    private boolean read;

    private mz.sisden.sisden.zkoss.ZkPage fromZkPage;

    @Init
    public void init(@ExecutionArgParam("MODULE") Module moduleArg,
                     @ExecutionArgParam("read") Boolean readArg,
                     @ExecutionArgParam("update") Boolean updateArg,
                     @ExecutionArgParam("fromZkPage") mz.sisden.sisden.zkoss.ZkPage fromZkPageArg) {
        if (moduleArg != null) {
            this.module = moduleArg;
            setUpdate(Boolean.TRUE.equals(updateArg));
            setRead(Boolean.TRUE.equals(readArg));
            setCreate(false);
        } else {
            this.module = new Module();
            setCreate(true);
            setUpdate(false);
            setRead(false);
        }
        this.fromZkPage = fromZkPageArg;
    }

    @Command
    @NotifyChange({"module", "create", "update"})
    public void saveOrUpdate() {
        try {
            this.getBeans().getModuleService().save(module);
            Messagebox.show("Módulo salvo com sucesso!", "Sucesso", Messagebox.OK, Messagebox.INFORMATION, event -> {
                this.getComponent().detach();
                org.zkoss.bind.BindUtils.postGlobalCommand(null, null, "refreshList", null);
            });
        } catch (Exception e) {
            log.error("Erro ao salvar módulo", e);
            Messagebox.show("Erro ao salvar módulo: " + e.getMessage(), "Erro", Messagebox.OK, Messagebox.ERROR);
        }
    }

    @Command
    public void back() {
        if (fromZkPage != null && fromZkPage == mz.sisden.sisden.zkoss.ZkPage.MODULE_LIST) {
            ZkUtils.getNavigateBuilder().ZkPage(mz.sisden.sisden.zkoss.ZkPage.MODULE_LIST).go();
        } else {
            ZkUtils.back(this.getComponent());
        }
    }

    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) org.zkoss.zk.ui.Component view) {
        this.setComponent(view);
    }
} 