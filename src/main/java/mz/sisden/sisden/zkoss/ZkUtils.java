/*
 * Copyright (c) 2025
 * EACUAMBA
 * All rights reserved.
 * Created by Edilson Alexandre Cuamba (@eacuamba) on 04/04/2025
 */

package mz.sisden.sisden.zkoss;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import mz.sisden.sisden.configuration.AppContext;
import mz.sisden.sisden.configuration.security.Securities;
import mz.sisden.sisden.entities.SystemFile;
import mz.sisden.sisden.entities.User;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.zkoss.bind.BindUtils;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Window;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Boolean.TRUE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static mz.sisden.sisden.zkoss.ZkArgument.NAVIGATOR;
import static mz.sisden.sisden.zkoss.ZkArgument.SYSTEM_FILE;
import static mz.sisden.sisden.zkoss.ZkPage.SYSTEM_FILE_VIEWER;
import static org.apache.commons.collections4.MapUtils.isEmpty;
import static org.apache.commons.collections4.MapUtils.isNotEmpty;
import static org.apache.commons.lang3.BooleanUtils.isTrue;
import static org.apache.commons.lang3.StringUtils.remove;
import static org.zkoss.bind.annotation.Scope.EXECUTION;
import static org.zkoss.bind.annotation.Scope.SESSION;
import static org.zkoss.zk.ui.Executions.getCurrent;

@Slf4j
public class ZkUtils {
    public static void prepareViewState(IZkState iZkState, Boolean popup, Boolean read, Boolean update, Boolean singleSelection, Boolean multipleSelection) {
        if (nonNull(popup))
            iZkState.setPopup(popup);

        if (nonNull(read))
            iZkState.setRead(read);

        if (nonNull(update))
            iZkState.setUpdate(update);

        if (nonNull(singleSelection))
            iZkState.setSingleSelection(singleSelection);

        if (nonNull(multipleSelection))
            iZkState.setMultipleSelection(multipleSelection);

        iZkState.setCreate(!iZkState.getUpdate() && !iZkState.getRead());

        iZkState.setVisible(!iZkState.getRead());
        iZkState.setReadonly(iZkState.getRead());
        iZkState.setButtonVisible(!iZkState.getRead());
        iZkState.setDisabled(iZkState.getRead());
        iZkState.setCreateOrUpdate(iZkState.getCreate() || iZkState.getUpdate());
        iZkState.setReadOrUpdate(iZkState.getRead() || iZkState.getUpdate());

        iZkState.setSelection(iZkState.getSingleSelection() || iZkState.getMultipleSelection());
        BindUtils.postNotifyChange(iZkState, "popup", "read", "update", "singleSelection", "multipleSelection", "selection", "create", "createOrUpdate", "readOrUpdate", "buttonVisible", "visible", "readonly", "disabled");
    }

    public static void turnToReadonlyState(IZkState iZkState) {
        prepareViewState(iZkState, iZkState.getPopup(), true, false, iZkState.getSingleSelection(), iZkState.getMultipleSelection());
        BindUtils.postNotifyChange(iZkState, ".");
    }

    public static void updateState(IZkState iZkState) {
        prepareViewState(iZkState, iZkState.getPopup(), false, true, iZkState.getSingleSelection(), iZkState.getMultipleSelection());
    }

    public static Component getRootComponent() {
        Component rootComponent = getCurrent().getDesktop().getFirstPage().getLastRoot();
        log.debug("Root component is {}", rootComponent);
        return rootComponent;
    }

    @SuppressWarnings({"unchecked"})
    public <T extends Component> T getComponent(String id, Boolean inAllIdScopes) {
        if (isTrue(inAllIdScopes)) {
            return (T) Executions.getCurrent().getDesktop().getComponents().stream().filter(c -> c.getId().equals(remove(id, "#"))).findFirst().orElse(null);
        }
        return (T) getRootComponent().query("#" + remove(id, "#"));
    }

    public static Arguments getArgumentsBuilder() {
        return new Arguments();
    }

    @Getter
    public static class Arguments {
        private final Map<String, Object> arguments = new HashMap<>();

        public Arguments add(ZkArgument zkArgument, Object value) {
            zkArgument.put(arguments, value);
            return this;
        }
    }

    public static ModalBuilder getModalBuilder() {
        return new ModalBuilder();
    }

    public static class ModalBuilder {
        private ZkPage zkPage;
        private ZkPage fromZkPage;
        private Component parentComponent;
        private Boolean read = Boolean.FALSE;
        private Boolean update = Boolean.FALSE;
        private Boolean singleSelection = Boolean.FALSE;
        private Boolean multipleSelection = Boolean.FALSE;
        private Map<String, Object> arguments;

        public ModalBuilder zkPage(ZkPage zkPage) {
            this.zkPage = zkPage;
            return ModalBuilder.this;
        }

        public ModalBuilder fromZkPage(ZkPage fromZkPage) {
            this.fromZkPage = fromZkPage;
            return ModalBuilder.this;
        }

        public ModalBuilder parentComponent(Component parentComponent) {
            this.parentComponent = parentComponent;
            return ModalBuilder.this;
        }

        public ModalBuilder readonly(boolean read) {
            this.read = read;
            return ModalBuilder.this;
        }

        public ModalBuilder readonly() {
            this.read = true;
            return ModalBuilder.this;
        }

        public ModalBuilder update(Boolean update) {
            this.update = update;
            return ModalBuilder.this;
        }

        public ModalBuilder update() {
            this.update = true;
            return ModalBuilder.this;
        }

        public ModalBuilder singleSelection(Boolean singleSelection) {
            this.singleSelection = singleSelection;
            return ModalBuilder.this;
        }

        public ModalBuilder singleSelection() {
            this.singleSelection = true;
            return ModalBuilder.this;
        }

        public ModalBuilder multipleSelection(Boolean multipleSelection) {
            this.multipleSelection = multipleSelection;
            return ModalBuilder.this;
        }

        public ModalBuilder multipleSelection() {
            this.multipleSelection = true;
            return ModalBuilder.this;
        }

        public ModalBuilder arguments(Map<String, Object> arguments) {
            this.arguments = arguments;
            return ModalBuilder.this;
        }

        public ModalBuilder withArgument(ZkArgument zkArgument, Object value) {
            this.arguments = zkArgument.put(this.arguments, value);
            return ModalBuilder.this;
        }

        public ModalBuilder withArgument(ZkArgument zkArgument) {
            this.arguments = zkArgument.put(this.arguments, true);
            return ModalBuilder.this;
        }

        public Window show() {
            if (isNull(this.zkPage))
                throw new RuntimeException("The zkPage is mandatory, i just tried to find it for you.!");

            if (isNull(this.parentComponent))
                ZkAlerts.error("Please specify a parent component.");

            if (isEmpty(this.arguments)) {
                this.arguments = new HashMap<>();
            }

            this.arguments = ZkArgument.read.putIn(this.arguments, this.read);
            this.arguments = ZkArgument.update.putIn(this.arguments, this.update);
            this.arguments = ZkArgument.singleSelection.putIn(this.arguments, this.singleSelection);
            this.arguments = ZkArgument.multipleSelection.putIn(this.arguments, this.multipleSelection);
            this.arguments = ZkArgument.FROM_ZK_PAGE.putIn(this.arguments, this.fromZkPage);
            this.arguments = ZkArgument.ZK_PAGE.putIn(this.arguments, this.zkPage);

            return showModal(this.zkPage, this.fromZkPage, this.parentComponent, this.arguments);
        }

        public Window go() {
            return this.show();
        }

        public <T extends Event> Window showAndListen(ZkEvent zkEvent, EventListener<T> eventListener) {
            Window window = this.show();
            zkEvent.listen(
                    window,
                    eventListener
            );
            return window;
        }

        public <T extends Event> Window showAndListen(String event, EventListener<T> eventListener) {
            Window window = this.show();
            window.addEventListener(
                    event,
                    eventListener
            );
            return window;
        }

        public <T extends Event> Window onClose(EventListener<T> eventListener) {
            Window window = this.show();
            window.addEventListener(
                    Events.ON_CLOSE,
                    eventListener
            );
            return window;
        }
    }

    public static NavigatorBuilder getNavigateBuilder() {
        return new NavigatorBuilder();
    }

    public static class NavigatorBuilder {
        private ZkPage zkPage;
        private ZkPage fromZkPage;
        private Boolean read = Boolean.FALSE;
        private Boolean update = Boolean.FALSE;
        private Boolean singleSelection = Boolean.FALSE;
        private Boolean multipleSelection = Boolean.FALSE;
        private Map<String, Object> arguments;

        public NavigatorBuilder ZkPage(ZkPage zkPage) {
            this.zkPage = zkPage;
            return NavigatorBuilder.this;
        }

        public NavigatorBuilder fromZkPage(ZkPage fromZkPage) {
            this.fromZkPage = fromZkPage;
            return NavigatorBuilder.this;
        }

        public NavigatorBuilder read(Boolean read) {
            this.read = read;
            return NavigatorBuilder.this;
        }

        public NavigatorBuilder readonly() {
            this.read = true;
            return NavigatorBuilder.this;
        }

        public NavigatorBuilder update(Boolean update) {
            this.update = update;
            return NavigatorBuilder.this;
        }

        public NavigatorBuilder update() {
            this.update = true;
            return NavigatorBuilder.this;
        }

        public NavigatorBuilder singleSelection(Boolean singleSelection) {
            this.singleSelection = singleSelection;
            return NavigatorBuilder.this;
        }

        public NavigatorBuilder singleSelection() {
            this.singleSelection = true;
            return NavigatorBuilder.this;
        }

        public NavigatorBuilder multipleSelection(Boolean multipleSelection) {
            this.multipleSelection = multipleSelection;
            return NavigatorBuilder.this;
        }

        public NavigatorBuilder multipleSelection() {
            this.multipleSelection = true;
            return NavigatorBuilder.this;
        }

        public NavigatorBuilder arguments(Map<String, Object> arguments) {
            this.arguments = arguments;
            return NavigatorBuilder.this;
        }

        public NavigatorBuilder withArgument(ZkArgument zkArgument, Object value) {
            this.arguments = zkArgument.put(this.arguments, value);
            return NavigatorBuilder.this;
        }

        public NavigatorBuilder withArgument(ZkArgument zkArgument) {
            this.arguments = zkArgument.put(this.arguments, true);
            return NavigatorBuilder.this;
        }

        public void go() {
            if (isNull(this.zkPage))
                throw new RuntimeException("The Page is mandatory, i just tried to find it for you.!");

            if (isNull(this.arguments) || isEmpty(this.arguments)) {
                this.arguments = new HashMap<>();
            }

            this.arguments.put("read", this.read);
            this.arguments.put("update", this.update);
            this.arguments.put("singleSelection", this.singleSelection);
            this.arguments.put("multipleSelection", this.multipleSelection);
            this.arguments.put(ZkArgument.ZK_PAGE.name(), this.zkPage);
            this.arguments.put(ZkArgument.FROM_ZK_PAGE.name(), this.fromZkPage);

            MapUtils.emptyIfNull(this.arguments).forEach((key, value) -> setAttribute(key, value, SESSION));
            getNavigator().go(this.zkPage, true);
        }

        public void show() {
            this.go();
        }
    }

    public static Navigator getNavigator() {
        Securities securities = AppContext.getByClass(Securities.class);

        Navigator navigator = getAttribute(NAVIGATOR.name(), SESSION);
        if (isNull(navigator)) {
            navigator = new Navigator(ZkPage.HOME, securities.getUser());
            setAttribute(NAVIGATOR.name(), navigator, SESSION);
        }

        return navigator;
    }

    public static void back(Component component) {
        if (nonNull(component) && component instanceof Window window && window.inModal()) {
            window.detach();
        } else {
            Navigator navigator = getNavigator();
            navigator.back();
        }

        if (nonNull(component)) {
            Events.postEvent(Events.ON_CLOSE, component, null);
        }
    }

    @SuppressWarnings({"unchecked"})
    public static Window showModal(
            ZkPage zkPage,
            ZkPage fromZkPage,
            Component parent,
            Object object
    ) {
        if (isNull(parent)) {
            throw new IllegalArgumentException("You should pass an component to be the parent of the modal.");
        }
        if (isNull(zkPage)) {
            ZkAlerts.warn("A página está nula!");
            log.warn("page is null, cannot showModal");
            return null;
        }

        String zulFile = zkPage.getPath();

        log.debug("Open page ({}),  zul file ({}) as Modal. User: {}", zkPage, zulFile, AppContext.findByClass(Securities.class).map(Securities::getUser).map(User::getName).orElse(null));

        Map<String, Object> arguments = new HashMap<>();
        arguments.put("modus", "modal");
        arguments.put("popup", TRUE);
        arguments.put(ZkArgument.FROM_ZK_PAGE.name(), fromZkPage);
        arguments.put(ZkArgument.ZK_PAGE.name(), zkPage);

        if (object instanceof Map m && isNotEmpty(m)) {
            arguments.putAll(m);
        }

        for (final Map.Entry<String, Object> entry : arguments.entrySet()) {
            setAttribute(entry.getKey(), entry.getValue(), EXECUTION);
        }

        Component parentModalComponent = Executions.createComponents(zulFile, parent, arguments);

        parentModalComponent = getWindow(parentModalComponent);

        if (parentModalComponent instanceof Window w) {
            w.setZclass("popup");

            if (w.getParent() != null) {
                w.doModal();
            }

            for (final Map.Entry<String, Object> entry : arguments.entrySet()) {
                w.setAttribute(entry.getKey(), entry.getValue());
            }

            return w;
        }

        return null;
    }

    public static Component getWindow(Component component) {
        if (component != null && !(component instanceof Window)) {
            return getWindow(component.getParent());
        }
        return component;
    }

    @SuppressWarnings({"unchecked"})
    public static <T> T getAttribute(String key, org.zkoss.bind.annotation.Scope scope) {
        if (StringUtils.isBlank(key)) {
            return null;
        }

        Execution execution = getCurrent();
        Session session = execution.getSession();

        switch (scope) {
            case EXECUTION: {
                return (T) execution.getAttribute(key);
            }

            case SESSION:
            default: {
                return (T) session.getAttribute(key);
            }
        }
    }

    /**
     * get attribute from execution and if not found from session.
     *
     * @param key the key
     * @param <T> class type of attribute
     * @return attribute found or null
     */
    @SuppressWarnings({"unchecked"})
    public static <T> T getAttribute(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }

        Execution execution = getCurrent();
        Session session = execution.getSession();

        T attribute = (T) execution.getAttribute(key);
        if (isNull(attribute)) {
            attribute = (T) session.getAttribute(key);
        }

        return attribute;
    }

    public static void clearAttributes(org.zkoss.bind.annotation.Scope scope, ZkArgument... zkArguments) {
        if (isNull(zkArguments)) {
            return;
        }

        Execution execution = getCurrent();
        Session session = execution.getSession();

        for (ZkArgument zkArgument : zkArguments) {
            switch (scope) {
                case EXECUTION: {
                    execution.removeAttribute(zkArgument.name());
                    break;
                }

                case SESSION:
                default: {
                    session.removeAttribute(zkArgument.name());
                    break;
                }
            }
        }
    }

    public static void setAttribute(String key, Object value, org.zkoss.bind.annotation.Scope scope) {
        if (StringUtils.isBlank(key)) {
            return;
        }

        Execution execution = getCurrent();
        Session session = execution.getSession();

        switch (scope) {
            case EXECUTION: {
                execution.setAttribute(key, value);
                break;
            }

            case SESSION:
            default: {
                session.setAttribute(key, value);
                break;
            }
        }

    }

    public static void showFile(Component parentComponent, SystemFile systemFile) {
        ZkUtils.getModalBuilder()
                .parentComponent(parentComponent)
                .zkPage(SYSTEM_FILE_VIEWER)
                .withArgument(SYSTEM_FILE, systemFile)
                .show();
    }

    public static AImage toAImage(SystemFile image) throws IOException {
        if (nonNull(image)) {
            String fileName = image.getFileName();
            byte[] byteData = image.getByteData();
            if (ArrayUtils.isNotEmpty(byteData)) {
                return new AImage(fileName, byteData);
            }

            String path = image.getPath();
            if (StringUtils.isNotBlank(path)) {
                Path path1 = Paths.get(path);
                if (Files.exists(path1)) {
                    return new AImage(fileName, Files.readAllBytes(path1));
                }
            }
        }
        return null;
    }
}
