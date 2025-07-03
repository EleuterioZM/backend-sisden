package mz.sisden.sisden.zkoss.view_model.user;

import lombok.Getter;
import lombok.Setter;
import mz.sisden.sisden.configuration.ZkComponent;
import mz.sisden.sisden.entities.User;
import mz.sisden.sisden.zkoss.*;
import org.zkoss.bind.annotation.*;

import java.util.Objects;

@Getter
@Setter
@ZkComponent
public class UserFormVM extends ZkViewModel {
    private User user;

    @Init(superclass = true)
    public void init(@ExecutionArgParam("USER") User userArg,
                     @ExecutionArgParam("read") Boolean readArg,
                     @ExecutionArgParam("update") Boolean updateArg) {
        if (userArg != null) {
            this.user = userArg;
            setUpdate(Boolean.TRUE.equals(updateArg));
            setRead(Boolean.TRUE.equals(readArg));
            setCreate(false);
        } else {
            this.user = new User();
            this.user.setActive(true); // Ativo por padrão
            this.user.setTries(5); // Tentativas padrão
            setCreate(true);
            setUpdate(false);
            setRead(false);
        }
    }

    @AfterCompose(superclass = true)
    public void afterCompose() {
        // O usuário já foi configurado no @Init
        // Se não foi passado via parâmetro, será criado um novo
    }

    @Command
    public void saveOrUpdate() {
        if (this.getCreate()) {
            saveOrUpdate("Criar", "Criado");
        } else if (this.getUpdate()) {
            saveOrUpdate("Actualizar", "Actualizado");
        }
    }

    public void saveOrUpdate(String message, String alertInfo) {
        ZkAlerts.builder()
                .question("Deseja " + message + " o Utilizador {}?", this.user.getUsername())
                .onYesListener(event -> {
                    this.getBeans().getUserSaver().save(this.user);

                    ZkAlerts.info("Utilizador " + alertInfo + " com sucesso!");
                    ZkUtils.back(this.getComponent());
                }).show();
    }
} 