package mz.sisden.sisden.zkoss.view_model.permission;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.entities.Permission;
import mz.sisden.sisden.entities.Module;
import mz.sisden.sisden.services.permission.PermissionService;
import mz.sisden.sisden.repositories.ModuleRepository;
import org.zkoss.bind.annotation.*;
import org.zkoss.zul.Messagebox;
import java.util.List;
import mz.sisden.sisden.zkoss.ZkViewModel;
import mz.sisden.sisden.zkoss.ZkUtils;
import mz.sisden.sisden.configuration.ZkComponent;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Component;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.bind.BindUtils;

@Slf4j
@Getter
@Setter
@ZkComponent
public class PermissionManagementFormVM extends ZkViewModel {
    private Permission permission;
    private List<Module> moduleList;
    private Module selectedModule;

    private boolean create;
    private boolean update;
    private boolean read;

    @Init
    public void init(@ExecutionArgParam("permission") Permission permissionArg,
                     @ExecutionArgParam("read") Boolean readArg) {
        try {
            moduleList = this.getBeans().getModuleRepository().findAll();
        } catch (Exception e) {
            log.error("Erro ao carregar módulos", e);
            moduleList = new java.util.ArrayList<>();
        }

        if (permissionArg != null) {
            this.permission = permissionArg;
            this.selectedModule = permissionArg.getModule();
            this.update = true;
            this.create = false;
        } else {
            this.permission = new Permission();
            this.create = true;
            this.update = false;
        }
        this.read = readArg != null && readArg;
    }

    @Command
    @NotifyChange({"permission", "create", "update"})
    public void saveOrUpdate() {
        try {
            permission.setModule(selectedModule);
            this.getBeans().getPermissionService().save(permission);
            Messagebox.show("Permissão salva com sucesso!", "Sucesso", Messagebox.OK, Messagebox.INFORMATION, event -> {
                this.getComponent().detach();
                BindUtils.postGlobalCommand(null, null, "refreshList", null);
            });
        } catch (Exception e) {
            log.error("Erro ao salvar permissão", e);
            Messagebox.show("Erro ao salvar permissão: " + e.getMessage(), "Erro", Messagebox.OK, Messagebox.ERROR);
        }
    }

    @Command
    public void back() {
        ZkUtils.back(this.getComponent());
    }

    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
        this.setComponent(view);
    }
} 