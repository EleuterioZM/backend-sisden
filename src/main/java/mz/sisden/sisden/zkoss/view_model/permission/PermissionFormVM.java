package mz.sisden.sisden.zkoss.view_model.permission;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.entities.*;
import mz.sisden.sisden.services.permission.InstitutionUserUserGroupPermissionService;
import mz.sisden.sisden.repositories.InstituitionRepository;
import mz.sisden.sisden.repositories.UserRepository;
import mz.sisden.sisden.repositories.UserGroupRepository;
import mz.sisden.sisden.repositories.PermissionRepository;
import org.zkoss.bind.annotation.*;
import org.zkoss.zul.Messagebox;
import java.util.List;
import mz.sisden.sisden.zkoss.ZkViewModel;
import mz.sisden.sisden.zkoss.ZkUtils;
import mz.sisden.sisden.configuration.ZkComponent;

@Slf4j
@Getter
@Setter
@ZkComponent
public class PermissionFormVM extends ZkViewModel {
    private InstitutionUserUserGroupPermission permission;
    private List<Instituition> instituitionList;
    private List<User> userList;
    private List<UserGroup> userGroupList;
    private List<Permission> permissionList;

    private Instituition selectedInstituition;
    private User selectedUser;
    private UserGroup selectedUserGroup;
    private Permission selectedPermission;

    private boolean create;
    private boolean update;
    private boolean read;

    @Init
    public void init(@ExecutionArgParam("permission") InstitutionUserUserGroupPermission permissionArg) {
        try {
            instituitionList = this.getBeans().getInstituitionRepository().findAll();
            userList = this.getBeans().getUserRepository().findAll();
            userGroupList = this.getBeans().getUserGroupRepository().findAll();
            permissionList = this.getBeans().getPermissionRepository().findAll();
        } catch (Exception e) {
            log.error("Erro ao carregar dados para PermissionFormVM", e);
            instituitionList = new java.util.ArrayList<>();
            userList = new java.util.ArrayList<>();
            userGroupList = new java.util.ArrayList<>();
            permissionList = new java.util.ArrayList<>();
        }

        if (permissionArg != null) {
            this.permission = permissionArg;
            this.selectedInstituition = permissionArg.getInstituition();
            this.selectedUser = permissionArg.getUser();
            this.selectedUserGroup = permissionArg.getUserGroup();
            this.selectedPermission = permissionArg.getPermission();
            this.update = true;
            this.create = false;
        } else {
            this.permission = new InstitutionUserUserGroupPermission();
            this.create = true;
            this.update = false;
        }
    }

    @Command
    @NotifyChange({"permission", "create", "update"})
    public void saveOrUpdate() {
        try {
            permission.setInstituition(selectedInstituition);
            permission.setUser(selectedUser);
            permission.setUserGroup(selectedUserGroup);
            permission.setPermission(selectedPermission);
            this.getBeans().getInstitutionUserUserGroupPermissionService().save(permission);
            Messagebox.show("Permissão salva com sucesso!", "Sucesso", Messagebox.OK, Messagebox.INFORMATION, event -> {
                this.getComponent().detach();
                org.zkoss.bind.BindUtils.postGlobalCommand(null, null, "refreshList", null);
            });
        } catch (Exception e) {
            log.error("Erro ao salvar permissão", e);
            Messagebox.show("Erro ao salvar permissão: " + e.getMessage(), "Erro", Messagebox.OK, Messagebox.ERROR);
        }
    }

    @Command
    public void back() {
        mz.sisden.sisden.zkoss.ZkUtils.getNavigateBuilder()
            .ZkPage(mz.sisden.sisden.zkoss.ZkPage.PERMISSION_LIST)
            .go();
    }

    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) org.zkoss.zk.ui.Component view) {
        this.setComponent(view);
    }
}
