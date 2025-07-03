/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.zkoss;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import mz.sisden.sisden.utils.Beans;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.bind.annotation.Scope;
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Button;

import static java.util.Objects.nonNull;

@Getter
@Setter
@VariableResolver(value = DelegatingVariableResolver.class)
public abstract class ZkController extends SelectorComposer<Component> implements IZkState {
    private Boolean popup = Boolean.FALSE;

    private Boolean create = Boolean.FALSE;
    private Boolean update = Boolean.FALSE;
    private Boolean createOrUpdate = Boolean.FALSE;
    private Boolean read = Boolean.FALSE;
    private Boolean readOrUpdate = Boolean.FALSE;

    private Boolean multipleSelection = Boolean.FALSE;
    private Boolean singleSelection = Boolean.FALSE;
    private Boolean selection = Boolean.FALSE;

    private Boolean visible = Boolean.FALSE;
    private Boolean readonly = Boolean.FALSE;
    private Boolean disabled = Boolean.FALSE;
    private Boolean buttonVisible = Boolean.FALSE;

    private Session session;
    private Desktop desktop;
    private Component component;
    private Page page;
    private Execution execution;

    private ZkPage currZkPage;
    private ZkPage fromZkPage;

    @Autowired
    @Setter(AccessLevel.PRIVATE)
    private Beans beans;

    @Wire
    private Button buttonBack;

    @Override
    public ComponentInfo doBeforeCompose(Page page, Component parent, ComponentInfo compInfo) {
        this.setPage(page);

        ZkUtils.prepareViewState(
                this,
                ZkUtils.getAttribute(ZkArgument.popup.name(), Scope.EXECUTION),
                ZkUtils.getAttribute(ZkArgument.read.name(), Scope.EXECUTION),
                ZkUtils.getAttribute(ZkArgument.update.name(), Scope.EXECUTION),
                ZkUtils.getAttribute(ZkArgument.singleSelection.name(), Scope.EXECUTION),
                ZkUtils.getAttribute(ZkArgument.multipleSelection.name(), Scope.EXECUTION)
        );

        this.setCurrZkPage(ZkUtils.getAttribute(ZkArgument.ZK_PAGE.name()));
        this.setFromZkPage(ZkUtils.getAttribute(ZkArgument.FROM_ZK_PAGE.name()));

        return super.doBeforeCompose(page, parent, compInfo);
    }

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        this.setExecution(Executions.getCurrent());
        this.setDesktop(comp.getDesktop());
        this.setComponent(comp);
        this.setSession(session);

        if (nonNull(this.buttonBack)) {
            this.buttonBack.addEventListener(Events.ON_CLICK, event -> {
                ZkUtils.back(this.getComponent());
                event.stopPropagation();
            });
        }
    }
}
