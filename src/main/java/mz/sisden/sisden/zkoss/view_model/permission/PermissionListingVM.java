package mz.sisden.sisden.zkoss.view_model.permission;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.configuration.ZkComponent;
import mz.sisden.sisden.entities.Instituition;
import mz.sisden.sisden.entities.InstitutionUserUserGroupPermission;
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
import java.util.ArrayList;
import org.zkoss.bind.BindUtils;

@Slf4j
@Getter
@Setter
@ZkComponent
public class PermissionListingVM extends mz.sisden.sisden.zkoss.ZkViewModel {
    private ListModelList<InstitutionUserUserGroupPermission> permissionListModel;
    private List<Instituition> instituitionList;
    private Instituition selectedInstituition;

    @Init(superclass = true)
    public void init() {
    }

    @AfterCompose(superclass = true)
    public void afterCompose() {
        try {
            instituitionList = this.getBeans().getInstituitionRepository().findAll();
            permissionListModel = new ListModelList<>();
        } catch (Exception e) {
            log.error("Erro ao inicializar PermissionListingVM", e);
            instituitionList = new ArrayList<>();
            permissionListModel = new ListModelList<>();
        }
    }

    @Command
    @NotifyChange("permissionListModel")
    public void filterByInstituition() {
        if (selectedInstituition != null) {
            List<InstitutionUserUserGroupPermission> list = this.getBeans().getInstitutionUserUserGroupPermissionService().findByInstituition(selectedInstituition);
            permissionListModel = new ListModelList<>(list);
        } else {
            permissionListModel.clear();
        }
    }

    @Command
    public void addPermission() {
        ZkUtils.getModalBuilder()
                .parentComponent(this.getComponent())
                .zkPage(ZkPage.PERMISSION_FORM)
                .onClose(event -> filterByInstituition());
    }

    @Command
    @NotifyChange("permissionListModel")
    public void delete(@BindingParam("id") Long id) {
        org.zkoss.zul.Messagebox.show("Tem certeza que deseja remover esta permissão?", "Confirmação", new org.zkoss.zul.Messagebox.Button[]{org.zkoss.zul.Messagebox.Button.YES, org.zkoss.zul.Messagebox.Button.NO}, org.zkoss.zul.Messagebox.QUESTION, event -> {
            if (org.zkoss.zul.Messagebox.ON_YES.equals(event.getName())) {
                this.getBeans().getInstitutionUserUserGroupPermissionService().delete(id);
                org.zkoss.zul.Messagebox.show("Permissão removida com sucesso!", "Sucesso", org.zkoss.zul.Messagebox.OK, org.zkoss.zul.Messagebox.INFORMATION, evt -> {
                    filterByInstituition();
                    org.zkoss.bind.BindUtils.postNotifyChange(null, null, this, "permissionListModel");
                });
            }
        });
    }

    @Command
    public void edit(@BindingParam("id") Long id) {
        InstitutionUserUserGroupPermission permission = this.getBeans().getInstitutionUserUserGroupPermissionService().findById(id).orElse(null);
        if (permission != null) {
            java.util.Map<String, Object> args = new java.util.HashMap<>();
            args.put("permission", permission);
            mz.sisden.sisden.zkoss.ZkUtils.getModalBuilder()
                    .parentComponent(this.getComponent())
                    .zkPage(mz.sisden.sisden.zkoss.ZkPage.PERMISSION_FORM)
                    .arguments(args)
                    .update()
                    .onClose(event -> filterByInstituition());
        }
    }

    @org.zkoss.bind.annotation.GlobalCommand
    @NotifyChange("permissionListModel")
    public void refreshList() {
        if (selectedInstituition != null) {
            List<InstitutionUserUserGroupPermission> list = this.getBeans().getInstitutionUserUserGroupPermissionService().findByInstituition(selectedInstituition);
            permissionListModel = new org.zkoss.zul.ListModelList<>(list);
        } else {
            permissionListModel.clear();
        }
    }
} 