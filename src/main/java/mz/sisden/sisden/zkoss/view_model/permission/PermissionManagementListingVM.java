package mz.sisden.sisden.zkoss.view_model.permission;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.configuration.ZkComponent;
import mz.sisden.sisden.entities.Permission;
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
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Listbox;
import org.zkoss.zk.ui.Component;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.GlobalCommand;

@Slf4j
@Getter
@Setter
@ZkComponent
public class PermissionManagementListingVM extends mz.sisden.sisden.zkoss.ZkViewModel {
    private ListModelList<Permission> permissionListModel;

    @Wire
    private Listbox permissionList;

    @Init(superclass = true)
    public void init() {
    }

    @AfterCompose(superclass = true)
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
        org.zkoss.zk.ui.select.Selectors.wireComponents(view, this, false);
        try {
            List<Permission> permissions = this.getBeans().getPermissionService().findAll();
            permissionListModel = new ListModelList<>(permissions);
        } catch (Exception e) {
            log.error("Erro ao inicializar PermissionManagementListingVM", e);
            permissionListModel = new ListModelList<>();
        }
    }

    @Command
    public void addPermission() {
        ZkUtils.getModalBuilder()
                .parentComponent(this.getComponent())
                .zkPage(ZkPage.PERMISSION_MANAGEMENT_FORM)
                .onClose(event -> refreshList());
    }

    @Command
    @NotifyChange("permissionListModel")
    public void delete(@BindingParam("id") Long id) {
        Messagebox.show("Tem certeza que deseja apagar esta permissão?", "Confirmação", new Messagebox.Button[] {Messagebox.Button.YES, Messagebox.Button.NO}, Messagebox.QUESTION, event -> {
            if (Messagebox.ON_YES.equals(event.getName())) {
                this.getBeans().getPermissionService().delete(id);
                refreshList();
                BindUtils.postNotifyChange(null, null, this, "permissionListModel");
            }
        });
    }

    @Command
    public void edit(@BindingParam("id") Long id) {
        Permission permission = this.getBeans().getPermissionService().findById(id).orElse(null);
        if (permission != null) {
            java.util.Map<String, Object> args = new java.util.HashMap<>();
            args.put("permission", permission);
            ZkUtils.getModalBuilder()
                    .parentComponent(this.getComponent())
                    .zkPage(ZkPage.PERMISSION_MANAGEMENT_FORM)
                    .arguments(args)
                    .update()
                    .onClose(event -> refreshList());
        }
    }

    @Command
    public void view(@BindingParam("id") Long id) {
        Permission permission = this.getBeans().getPermissionService().findById(id).orElse(null);
        if (permission != null) {
            java.util.Map<String, Object> args = new java.util.HashMap<>();
            args.put("permission", permission);
            args.put("read", true);
            ZkUtils.getModalBuilder()
                    .parentComponent(this.getComponent())
                    .zkPage(ZkPage.PERMISSION_MANAGEMENT_FORM)
                    .arguments(args)
                    .readonly()
                    .onClose(event -> refreshList());
        }
    }

    @GlobalCommand
    @NotifyChange("permissionListModel")
    public void refreshList() {
        List<Permission> permissions = this.getBeans().getPermissionService().findAll();
        permissionListModel = new ListModelList<>(permissions);
        if (permissionList != null) {
            permissionList.invalidate();
        }
    }
} 