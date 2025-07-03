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
import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;

import static java.util.Objects.nonNull;

@Getter
@Setter
public abstract class ZkViewModel implements IZkState {
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
    private Boolean disabled = Boolean.FALSE;
    private Boolean readonly = Boolean.FALSE;
    private Boolean buttonVisible = Boolean.FALSE;

    private Session session;
    private Desktop desktop;
    private Component component;
    private org.zkoss.zk.ui.Page page;
    private Execution execution;
    private BindContext bindContext;

    private ZkPage currZkPage;
    private ZkPage fromZkPage;

    @Autowired
    @Setter(AccessLevel.PRIVATE)
    private Beans beans;

    @Wire
    private Button buttonBack;

    @Init(superclass = true)
    public void initZkViewModel(
            @ContextParam(ContextType.DESKTOP) Desktop desktop,
            @ContextParam(ContextType.PAGE) org.zkoss.zk.ui.Page page,
            @ContextParam(ContextType.COMPONENT) Component component,
            @ContextParam(ContextType.BIND_CONTEXT) BindContext bindContext,
            @ContextParam(ContextType.EXECUTION) Execution execution,
            @ContextParam(ContextType.SESSION) Session session
    ) {
        this.setExecution(execution);
        this.setDesktop(desktop);
        this.setPage(page);
        this.setComponent(component);
        this.setBindContext(bindContext);
        this.setSession(session);

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
    }

    @AfterCompose(superclass = true)
    public void afterComposeZkViewModel() {
        Selectors.wireComponents(this.getComponent(), this, false);
        Selectors.wireEventListeners(this.getComponent(), this);

        if (nonNull(this.buttonBack)) {
            this.buttonBack.addEventListener(Events.ON_CLICK, event -> {
                ZkUtils.back(this.getComponent());
                event.stopPropagation();
            });
        }
    }
}
