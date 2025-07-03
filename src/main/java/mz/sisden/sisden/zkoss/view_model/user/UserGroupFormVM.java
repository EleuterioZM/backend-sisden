/*
 * Copyright (c) 2025
 * 
 * All rights reserved.
 * Created by Eleuterio Zacarias Mabecuane  
 * */
package mz.sisden.sisden.zkoss.view_model.user;

import lombok.Getter;
import lombok.Setter;
import mz.sisden.sisden.configuration.ZkComponent;
import mz.sisden.sisden.entities.UserGroup;
import mz.sisden.sisden.services.user.UserGroupService;
import mz.sisden.sisden.zkoss.*;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

import java.util.Objects;

@Getter
@Setter
@ZkComponent
public class UserGroupFormVM extends ZkViewModel {

    private UserGroup userGroup;

    @Init(superclass = true)
    public void init() {
    }

    @AfterCompose(superclass = true)
    public void afterCompose() {
        this.userGroup = ZkUtils.getAttribute(ZkArgument.USER_GROUP.name());

        if (Objects.isNull(this.userGroup)) {
            this.userGroup = new UserGroup();
        }
    }

    @Command
    public void save() {
        if (this.getCreate()) {
            saveOrUpdate("Criar", "Criado");
        } else if (this.getUpdate()) {
            saveOrUpdate("Actualizar", "Actualizado");
        }
    }

    public void saveOrUpdate(String message, String alertInfo) {
        ZkAlerts.builder()
                .question("Deseja " + message + " o Grupo de Utilizadores {}?", this.userGroup.getName())
                .onYesListener(event -> {
                    this.getBeans().getUserGroupService().save(this.userGroup);
                    ZkAlerts.info("Grupo de Utilizadores " + alertInfo + " com sucesso!");
                    ZkUtils.back(this.getComponent());
                })
                .show();
    }

    @Command
    public void cancel() {
        try {
            // Fechar o modal
            Window window = (Window) org.zkoss.zk.ui.Executions.getCurrent().getDesktop().getFirstPage().getFellow("window");
            window.detach();
        } catch (Exception e) {
            Clients.showNotification("Erro ao cancelar: " + e.getMessage(), 
                                   Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 3000);
        }
    }
} 