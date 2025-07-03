package mz.sisden.sisden.zkoss.view_model.instituition;

import lombok.Getter;
import lombok.Setter;
import mz.sisden.sisden.configuration.ZkComponent;
import mz.sisden.sisden.entities.Instituition;
import mz.sisden.sisden.entities.User;
import mz.sisden.sisden.zkoss.ZkAlerts;
import mz.sisden.sisden.zkoss.ZkArgument;
import mz.sisden.sisden.zkoss.ZkUtils;
import mz.sisden.sisden.zkoss.ZkViewModel;
import org.apache.commons.collections4.CollectionUtils;
import org.zkoss.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@ZkComponent
public class InstituitionFormVM extends ZkViewModel {

    private Instituition instituition;

    private List<User> userList;

    private List<User> selectedUsers = new ArrayList<>();

    @Init(superclass = true)
    public void init(@ExecutionArgParam("instituition") Instituition instituitionArg,
                     @ExecutionArgParam("read") Boolean readArg,
                     @ExecutionArgParam("update") Boolean updateArg) {
        if (instituitionArg != null) {
            this.instituition = instituitionArg;
            setUpdate(Boolean.TRUE.equals(updateArg));
            setRead(Boolean.TRUE.equals(readArg));
            setCreate(false);
        } else {
            this.instituition = new Instituition();
            setCreate(true);
            setUpdate(false);
            setRead(false);
        }
    }


    @AfterCompose(superclass = true)
    public void afterCompose() {
        this.instituition = ZkUtils.getAttribute(ZkArgument.INSTITUITION.name());

        this.userList = this.getBeans().getUserRepository().findAll();

        if (this.instituition != null && CollectionUtils.isNotEmpty(this.instituition.getUsers())) {
            this.selectedUsers = this.instituition.getUsers();
        }

        if (Objects.isNull(this.instituition)) {
            this.instituition = new Instituition();
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
                .question("Deseja " + message + " a Instituição {}?", this.instituition.getName())
                .onYesListener(event -> {
                    // Atualiza usuários selecionados na entidade
                    this.instituition.setUsers(new ArrayList<>(selectedUsers));
                    this.getBeans().getInstituitionRepository().save(this.instituition);

                    ZkAlerts.info("Instituição " + alertInfo + " com sucesso!");
                    ZkUtils.back(this.getComponent());
                }).show();
    }
}
