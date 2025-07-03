/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.zkoss.controller.user;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.configuration.ZkComponent;
import mz.sisden.sisden.entities.User;
import mz.sisden.sisden.zkoss.ZkAlerts;
import mz.sisden.sisden.zkoss.ZkController;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;

@Slf4j
@Getter
@Setter
@ZkComponent
public class UserFormController extends ZkController {

    @Wire
    Textbox textboxUsername, textboxPassword;

    @Wire
    Button buttonSave, buttonUpdate;

    @Override
    public ComponentInfo doBeforeCompose(Page page, Component parent, ComponentInfo compInfo) {
        return super.doBeforeCompose(page, parent, compInfo);
    }

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
    }

    @Listen("onClick = #buttonSave")
    public void onClickButtonSave() {
        String username = this.textboxUsername.getValue();
        String password = this.textboxPassword.getValue();

        //validação simples

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        this.getBeans().getUserSaver().save(user);

        ZkAlerts.info("Utilizador registado com sucesso!");
    }
}
